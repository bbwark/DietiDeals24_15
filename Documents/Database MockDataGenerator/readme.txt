=== ITA ===

1) Esegui il seguente comando:
pip install faker
2) Eseguire il file generator.py, questo produrrà un nuovo file mock_data.sql all'interno della medesima folder
3) Se stai facendo uso di una macchina virtuale per l'esecuzione del database, caricare il file sulla macchina virtuale
4) Se stai eseguendo il database su container docker esegui il comando:
docker cp /home/ubuntu/mock_data.sql dietideals_ps_database:/mock_data.sql
questo serve a copiare il file sql in una cartella accessibile dal container
5) Per eseguire il file sql all'interno del database sulla macchina virtuale esegui il comando:
sudo docker exec -it dietideals_ps_database psql -U postgres -d dietidealsdatabase -f /mock_data.sql


=== ENG ===

1) Run the command:
pip install faker
2) Run the generator.py file, this will produce a new mock_data.sql file inside the same folder
3) If you are using a virtual machine to run the database, upload the file to the virtual machine
4) If you are running the database on docker container run the command:
docker cp /home/ubuntu/mock_data.sql dietideals_ps_database:/mock_data.sql
This is to copy the sql file to a folder accessible from the container
5) To execute the sql file inside the database on the virtual machine run the command:
sudo docker exec -it dietideals_ps_database psql -U postgres -d dietidealsdatabase -f /mock_data.sql