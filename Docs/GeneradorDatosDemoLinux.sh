#!/bin/bash

# Array of sample data for events
declare -a nombres_eventos=("Concierto Coldplay" "Partido Real Madrid vs Barcelona" "Feria del Libro" "Festival de Cine" "Conferencia de Tecnología" "Maratón de Nueva York" "Concierto de U2" "Partido de la NBA" "Exposición de Arte" "Festival de Música Electrónica" "Conferencia de Medicina" "Torneo de Tenis" "Feria de Gastronomía" "Festival de Jazz" "Conferencia de Negocios" "Carrera de Autos" "Concierto de Beyoncé" "Partido de la NFL" "Exposición de Fotografía" "Festival de Teatro")
declare -a descripciones_eventos=("Descripción1" "Descripción2" "Descripción3" "Descripción4" "Descripción5" "Descripción6" "Descripción7" "Descripción8" "Descripción9" "Descripción10" "Descripción11" "Descripción12" "Descripción13" "Descripción14" "Descripción15" "Descripción16" "Descripción17" "Descripción18" "Descripción19" "Descripción20")
declare -a paises=("España" "Francia" "Alemania" "Italia" "Portugal" "Reino Unido" "Estados Unidos" "Canadá" "México" "Brasil" "Argentina" "Chile" "Colombia" "Perú" "Venezuela" "Uruguay" "Paraguay" "Bolivia" "Ecuador" "Costa Rica")
declare -a ciudades=("Madrid" "París" "Berlín" "Roma" "Lisboa" "Londres" "Nueva York" "Toronto" "Ciudad de México" "Río de Janeiro" "Buenos Aires" "Santiago" "Bogotá" "Lima" "Caracas" "Montevideo" "Asunción" "La Paz" "Quito" "San José")
declare -a direcciones=("Dirección1" "Dirección2" "Dirección3" "Dirección4" "Dirección5" "Dirección6" "Dirección7" "Dirección8" "Dirección9" "Dirección10" "Dirección11" "Dirección12" "Dirección13" "Dirección14" "Dirección15" "Dirección16" "Dirección17" "Dirección18" "Dirección19" "Dirección20")
declare -a fechas=("2024-09-13" "2024-09-14" "2024-09-15" "2024-09-16" "2024-09-17" "2024-09-18" "2024-09-19" "2024-09-20" "2024-09-21" "2024-09-22" "2024-09-23" "2024-09-24" "2024-09-25" "2024-09-26" "2024-09-27" "2024-09-28" "2024-09-29" "2024-09-30" "2024-10-01" "2024-10-02")
declare -a horasInicio=("10:00" "11:00" "12:00" "13:00" "14:00" "15:00" "16:00" "17:00" "18:00" "19:00" "20:00" "21:00" "22:00" "23:00" "09:00" "08:00" "07:00" "06:00" "05:00" "04:00")
declare -a horasFinal=("12:00" "13:00" "14:00" "15:00" "16:00" "17:00" "18:00" "19:00" "20:00" "21:00" "22:00" "23:00" "00:00" "01:00" "10:00" "09:00" "08:00" "07:00" "06:00" "05:00")

# Array of sample data for tipos de apuestas
declare -a nombres_apuestas=("Apuesta Ganador" "Apuesta Perdedor" "Apuesta Empate" "Apuesta Goleador" "Apuesta Tiempo" "Apuesta Puntos" "Apuesta Sets" "Apuesta Rondas" "Apuesta Vueltas" "Apuesta Posición" "Apuesta Tarjetas" "Apuesta Faltas" "Apuesta Penales" "Apuesta Asistencias" "Apuesta Rebotes" "Apuesta Triples" "Apuesta Cuartos" "Apuesta Mitades" "Apuesta Final" "Apuesta Parcial")
declare -a descripciones_apuestas=("Descripción1" "Descripción2" "Descripción3" "Descripción4" "Descripción5" "Descripción6" "Descripción7" "Descripción8" "Descripción9" "Descripción10" "Descripción11" "Descripción12" "Descripción13" "Descripción14" "Descripción15" "Descripción16" "Descripción17" "Descripción18" "Descripción19" "Descripción20")
declare -a maximas=(2800 3000 2500 2700 2900 3100 3200 3300 3400 3500 3600 3700 3800 3900 4000 4100 4200 4300 4400 4500)
declare -a minimas=(500 600 700 800 900 1000 1100 1200 1300 1400 1500 1600 1700 1800 1900 2000 2100 2200 2300 2400)
declare -a combinadas=(true false true false true false true false true false true false true false true false true false true false)

# Loop to create 20 events and their corresponding tipos de apuestas
for i in {0..19}
do
  # Create event
  curl 'http://localhost:5173/api/eventos' -X POST \
    -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:130.0) Gecko/20100101 Firefox/130.0' \
    -H 'Accept: application/json, text/plain, */*' \
    -H 'Accept-Language: en-US,en;q=0.5' \
    -H 'Accept-Encoding: gzip, deflate, br, zstd' \
    -H 'Content-Type: application/json' \
    -H 'Origin: http://localhost:5173' \
    -H 'Connection: keep-alive' \
    -H 'Referer: http://localhost:5173/crear-evento' \
    -H 'Sec-Fetch-Dest: empty' \
    -H 'Sec-Fetch-Mode: cors' \
    -H 'Sec-Fetch-Site: same-origin' \
    -H 'Priority: u=0' \
    --data-raw "{\"nombre\":\"${nombres_eventos[$i]}\",\"descripcion\":\"${descripciones_eventos[$i]}\",\"pais\":\"${paises[$i]}\",\"ciudad\":\"${ciudades[$i]}\",\"direccion\":\"${direcciones[$i]}\",\"fecha\":\"${fechas[$i]}\",\"horaInicio\":\"${horasInicio[$i]}\",\"horaFinal\":\"${horasFinal[$i]}\",\"tiposDeApuestas\":[]}"

  # Create tipo de apuesta for the event
  curl 'http://localhost:5173/api/tipoapuestas' -X POST \
    -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:130.0) Gecko/20100101 Firefox/130.0' \
    -H 'Accept: application/json, text/plain, */*' \
    -H 'Accept-Language: en-US,en;q=0.5' \
    -H 'Accept-Encoding: gzip, deflate, br, zstd' \
    -H 'Content-Type: application/json' \
    -H 'Origin: http://localhost:5173' \
    -H 'Connection: keep-alive' \
    -H 'Referer: http://localhost:5173/crear-tipo-apuesta' \
    -H 'Sec-Fetch-Dest: empty' \
    -H 'Sec-Fetch-Mode: cors' \
    -H 'Sec-Fetch-Site: same-origin' \
    -H 'Priority: u=0' \
    --data-raw "{\"nombre\":\"${nombres_apuestas[$i]}\",\"descripcion\":\"${descripciones_apuestas[$i]}\",\"maxima\":${maximas[$i]},\"minima\":${minimas[$i]},\"combinada\":${combinadas[$i]},\"evento\":{\"id\":$((i+1)),\"nombre\":\"${nombres_eventos[$i]}\",\"descripcion\":\"${descripciones_eventos[$i]}\",\"pais\":\"${paises[$i]}\",\"ciudad\":\"${ciudades[$i]}\",\"direccion\":\"${direcciones[$i]}\",\"fecha\":\"${fechas[$i]}\",\"horaInicio\":\"${horasInicio[$i]}\",\"horaFinal\":\"${horasFinal[$i]}\",\"tiposDeApuestas\":[]}}"
done