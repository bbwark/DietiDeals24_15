import uuid
import random
from faker import Faker

# Utilizzare Faker per generare dati casuali realistici
fake = Faker()

# Configurazione dei parametri
NUM_USERS = 200
NUM_AUCTIONS = 1000
NUM_BIDS = 3000

# Genera un UUID
def generate_uuid():
    return str(uuid.uuid4())

# Genera UUID per i ruoli
role_user_uuid = generate_uuid()
role_admin_uuid = generate_uuid()

# Creazione dei ruoli con UUID validi
roles = [
    (role_user_uuid, 'USER'),
    (role_admin_uuid, 'ADMIN')
]


# Lista utenti e dizionario per mappare l'UUID dell'utente ai suoi dati
users = []
user_uuids = []
user_role_junction = []

# Generazione utenti
for _ in range(NUM_USERS):
    user_id = generate_uuid()
    user_uuids.append(user_id)
    name = fake.first_name()
    surname = fake.last_name()
    email = fake.email()
    password = fake.password()
    is_seller = fake.boolean(chance_of_getting_true=50)
    bio = fake.sentence(nb_words=6)
    address = fake.address().replace("\n", ", ")
    zip_code = fake.zipcode()
    country = fake.country().replace("'", "")
    phone_number = fake.phone_number()
    users.append((user_id, email, name, surname, password, is_seller, bio, address, zip_code, country, phone_number))
    
    # Collegamento con il ruolo USER
    user_role_junction.append((user_id, role_user_uuid))

# Generazione carte di credito
credit_cards = []
for user_id in user_uuids:
    if random.random() > 0.5:  # circa metà degli utenti avrà carte di credito
        for _ in range(random.randint(1, 3)):  # ogni utente avrà da 1 a 3 carte
            credit_card_number = fake.credit_card_number()
            expiration_date = fake.credit_card_expire()
            cvv = fake.credit_card_security_code()
            iban = fake.iban()
            credit_cards.append((credit_card_number, expiration_date, cvv, iban, user_id))

# Generazione aste
auctions = []
items = []
for _ in range(NUM_AUCTIONS):
    auction_id = generate_uuid()
    user_id = random.choice(user_uuids)  # owner scelto casualmente tra gli utenti
    auction_type = random.choice(['English', 'Silent'])
    category = random.choice(['Electronic', 'Games', 'House', 'Engine', 'Book', 'Fashion', 'Sport', 'Music', 'Other'])
    ending_date = fake.date_between(start_date='today', end_date='+30d')
    expired = fake.boolean(chance_of_getting_true=20)
    description = fake.text(max_nb_chars=200)
    min_step = str(random.randint(1, 10))
    interval = str(random.randint(1, 5))
    starting_price = str(round(random.uniform(10, 1000), 2))
    
    auctions.append((auction_id, user_id, auction_type, category, ending_date, expired, description, min_step, interval, starting_price))
    
    # Oggetto collegato all'asta
    item_id = generate_uuid()
    item_name = fake.word()
    item_image_url = fake.image_url()
    items.append((item_id, item_name, item_image_url, auction_id))

# Generazione offerte (bids)
bids = []
for _ in range(NUM_BIDS):
    bid_id = generate_uuid()
    value = round(random.uniform(10, 1000), 2)
    
    # Selezioniamo un'asta e assicuriamoci che l'utente non sia il proprietario
    selected_auction = random.choice(auctions)
    auction_id = selected_auction[0]
    auction_owner_id = selected_auction[1]
    
    # Seleziona un utente che NON sia il proprietario dell'asta
    user_id = random.choice([u for u in user_uuids if u != auction_owner_id])
    
    # Genera la data dell'offerta
    date = fake.date_time_this_year().strftime('%Y-%m-%d %H:%M:%S')
    bids.append((bid_id, value, user_id, auction_id, date))

# Generazione dello script SQL
with open('mock_data.sql', 'w') as f:
    # Ruoli
    f.write('-- Inserimento dei ruoli\n')
    for role in roles:
        f.write(f"INSERT INTO roles (role_id, authority) VALUES ('{role[0]}', '{role[1]}');\n")
    
    # Utenti
    f.write('\n-- Inserimento degli utenti\n')
    for user in users:
        f.write(f"INSERT INTO users (user_id, email, name, surname, password, is_seller, bio, address, zip_code, country, phone_number) "
                f"VALUES ('{user[0]}', '{user[1]}', '{user[2]}', '{user[3]}', '{user[4]}', {str(user[5]).upper()}, "
                f"'{user[6]}', '{user[7]}', '{user[8]}', '{user[9]}', '{user[10]}');\n")
    
    # Collegamento utenti e ruoli
    f.write('\n-- Collegamento utenti e ruoli\n')
    for ur in user_role_junction:
        f.write(f"INSERT INTO user_role_junction (user_id, role_id) VALUES ('{ur[0]}', '{ur[1]}');\n")
    
    # Carte di credito
    f.write('\n-- Inserimento delle carte di credito\n')
    for card in credit_cards:
        f.write(f"INSERT INTO cards (credit_card_number, expiration_date, cvv, iban, user_id) "
                f"VALUES ('{card[0]}', '{card[1]}', {card[2]}, '{card[3]}', '{card[4]}');\n")
    
    # Aste
    f.write('\n-- Inserimento delle aste\n')
    for auction in auctions:
        f.write(f"INSERT INTO auctions (id, user_id, type, category, ending_date, expired, description, min_step, interval, starting_price) "
                f"VALUES ('{auction[0]}', '{auction[1]}', '{auction[2]}', '{auction[3]}', '{auction[4]}', "
                f"{str(auction[5]).upper()}, '{auction[6]}', '{auction[7]}', '{auction[8]}', '{auction[9]}');\n")
    
    # Oggetti
    f.write('\n-- Inserimento degli oggetti\n')
    for item in items:
        f.write(f"INSERT INTO items (id, name, image_url, auction_id) VALUES ('{item[0]}', '{item[1]}', '{item[2]}', '{item[3]}');\n")
    
    # Offerte (bids)
    f.write('\n-- Inserimento delle offerte\n')
    for bid in bids:
        f.write(f"INSERT INTO bids (id, value, user_id, auction_id, date) "
                f"VALUES ('{bid[0]}', {bid[1]}, '{bid[2]}', '{bid[3]}', '{bid[4]}');\n")

print("File SQL generato con successo: 'mock_data.sql'")
