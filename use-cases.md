# Casos de uso API - MGCSS

## Caso 1 – Crear solicitud

Request:
POST /api/solicitudes

{
  "descripcion": "Incidencia"
}

Response esperado:
200 OK

{
  "id": 1,
  "descripcion": "Incidencia",
  "estado": "ABIERTA"
}

---

## Caso 2 – Obtener solicitud

Request:
GET /api/solicitudes/1

Response esperado:
200 OK

{
  "id": 1,
  "descripcion": "Incidencia",
  "estado": "ABIERTA"
}

---

## Caso 3 – Listar solicitudes

Request:
GET /api/solicitudes

Response esperado:
200 OK

[
  {
    "id": 1,
    "descripcion": "Incidencia",
    "estado": "ABIERTA"
  }
]

---

## Caso 4 – Asignar técnico

Request:
PUT /api/solicitudes/1/tecnico

{
  "nombre": "Ana",
  "activo": true
}

Response esperado:
200 OK

{
  "id": 1,
  "estado": "ABIERTA",
  "tecnicoAsignado": "Ana"
}

---

## Caso 5 – Cambiar estado

Request:
PUT /api/solicitudes/1/estado

{
  "estado": "EN_PROCESO"
}

Response esperado:
200 OK

{
  "id": 1,
  "estado": "EN_PROCESO"
}

---

## Caso 6 – Reabrir solicitud

Precondición:
Solicitud en estado CERRADA

Request:
PATCH /api/solicitudes/1/reabrir

Response esperado:
200 OK

{
  "id": 1,
  "estado": "ABIERTA"
}

---

## Caso 7 – Error: solicitud no encontrada

Request:
GET /api/solicitudes/999

Response esperado:
404 NOT FOUND

{
  "error": "Solicitud no encontrada con id: 999"
}

---

## Caso 8 – Error: validación

Request:
POST /api/solicitudes

{
  "descripcion": ""
}

Response esperado:
400 BAD REQUEST

{
  "descripcion": "La descripción no puede estar vacía"
}