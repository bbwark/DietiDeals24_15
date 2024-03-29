package com.CioffiDeVivo.dietideals.viewmodel

const val JWT_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1haWxAdGVzdC5jb20iLCJpYXQiOjE3MTE3MTg2MDksInJvbGVzIjoiVVNFUiJ9.HwNYS16pm06irsGh6WHztnADixMc7UuGTRZktf58a9c6Jxm1W8VIHHoy7hcett1y6YUIQs5FDXqOTNfvr6sZQqTqLo2gbDPllY1w05aXNOhkxcS-2Q4bl5jMQH8KQpqLGTu9Hc8MwavdLT_fno_8lLrVZMA4osaRiT542VWYfgJjeW1M_JQJ7Jend1XDvGoyRjs0OxXhLrxWmiRAuVzW7DYyDH-xOVEnlftPmUzVu84ATD2qrrSWm9YjdqVl9iDdj-P1uVxPTNSsRAIbZAldF-zbVdPr2XYnDJ8NT26iyzSLoARQmjThodFlvj-PZwh5TYJUltHTRPEKboV3cbF5ZA"

suspend fun test() {
    val client = HttpClient(CIO)
    val response: HttpResponse = client.get("https://16.171.206.112:8181/users/7e59c6e9-bbca-4612-b9d0-af1a886899ca") {
        header(HttpHeaders.Authorization, "Bearer $JWT_TOKEN")
    }
    println(response.bodyAsText())
}