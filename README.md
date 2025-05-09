## API Reference

### Registrar usuario

```http
  POST /api/auth/register
```

#### Body

```javascript
{
  "email": "usuario@email.com",
  "password": "password123",
  "role": "patient",
  "first_name": "Juan",
  "last_name": "Perez",
  "phone": "999999999",
  "birth_date": "1990-01-01",
  "dni": "12345678"
}


```

#### Response

```javascript
{
  "message": "Usuario registrado exitosamente",
  "user_id": 1
}

```

### Iniciar sesión

```http
  POST /api/auth/login
```

#### Body

```javascript
{
  "email": "usuario@email.com",
  "password": "password123"
}


```
#### Response

```javascript
{
  "token": "",
  "user": {
    "id": 1,
    "email": "usuario@email.com",
    "role": "patient"
  }
}

```


### Agendar cita

```http
  POST /api/appointments
```

#### Body

```javascript
{
  "appointment_date": "2025-05-02T10:00:00Z",
  "service_id": 2,
  "reason": "Limpieza dental",
  "patient_id": 1,
  "dentist_id": 3
}



```
#### Response

```javascript
{
  "message": "Cita agendada",
  "appointment_id": 5
}
```


### Registro de pagos

```http
  POST /api/payments
```

#### Body

```javascript
{
  "appointment_id": 5,
  "amount": 120.00,
  "payment_method": "Card"
}


```
#### Response

```javascript
{
  "message": "Pago registrado",
  "payment_id": 10
}
```

### Visualizar agenda de citas del odontólogo

```http
  GET /api/appointments?date=2025-05-01&user_id=3
```

#### Body

```javascript
{
}


```
#### Response

```javascript
[
  {
    "appointment_id": 1,
    "appointment_date": "2025-05-01T09:00:00Z",
    "service": "Limpieza Dental",
    "status": "Scheduled",
    "patient_name": "Ana López",
    "dentist_name": "Dr. María González"
  },
  {
    "appointment_id": 2,
    "appointment_date": "2025-05-01T10:00:00Z",
    "service": "Ortodoncia",
    "status": "Scheduled",
    "patient_name": "Carlos Ruiz",
    "dentist_name": "Dr. María González"
  }
]

```

### Crear historial clínico

```http
  POST /api/clinical
```

#### Body

```javascript
{
  "user_id": 1,
  "allergies": "Ninguna",
  "medical_history": "Hipertensión",
  "family_history": "Diabetes",
  "treatment_plan": "Ortodoncia completa",
  "evolution_notes": "Paciente responde bien al tratamiento"
}


```
#### Response

```javascript
{
  "message": "Historial clínico creado"
}
```


### Consulta de historial clínico

```http
  GET /api/clinical/{user_id}
```

#### Body

```javascript
{
}

```
#### Response

```javascript
{
  "allergies": "Ninguna",
  "medical_history": "Hipertensión",
  "family_history": "Diabetes",
  "treatment_plan": "Ortodoncia completa",
  "evolution_notes": "Paciente responde bien al tratamiento"
}
```


### Generar comprobante de pago

```http
  GET /api/payments/{payment_id}
```

#### Body

```javascript
{
}

```
#### Response

```javascript
{
  "payment_id": 10,
  "amount": 120.00,
  "payment_date": "2025-05-02T12:00:00Z",
  "method": "Card",
  "status": "Paid"
}
```


### Consulta de historial de pagos

```http
  GET /api/users/{user_id}/payments
```

#### Body

```javascript
{
}

```
#### Response

```javascript
[
  {
    "payment_id": 10,
    "appointment_id": 5,
    "amount": 120.00,
    "payment_date": "2025-05-02T12:00:00Z",
    "status": "Paid"
  }
]
```


### Leer mis citas

```http
  GET /api/users/{user_id}/appointments
```

#### Body

```javascript
{
}

```
#### Response

```javascript
[
  {
    "appointment_id": 5,
    "appointment_date": "2025-05-02T10:00:00Z",
    "service": "Limpieza dental",
    "status": "Scheduled",
    "dentist_name": "Dr. María González"
  }
]

```


### Leer perfil de usuario

```http
  GET /api/users/{user_id}/profile
```

#### Body

```javascript
{
}

```
#### Response

```javascript
{
  "user_id": 1,
  "first_name": "Juan",
  "last_name": "Perez",
  "phone": "999999999",
  "birth_date": "1990-01-01",
  "dni": "12345678",
  "role": "patient"
}

```


### Actualización de perfil de usuario

```http
    PUT /api/users/{user_id}/profile
```

#### Body

```javascript
{
  "first_name": "NuevoNombre",
  "last_name": "NuevoApellido",
  "phone": "888888888"
}


```
#### Response

```javascript
{
  "message": "Perfil actualizado"
}
```


### Recuperar contraseña

```http
  POST /api/auth/forgot-password
```

#### Body

```javascript
{
  "email": "usuario@email.com"
}


```
#### Response

```javascript
{
  "message": "Se envió un correo para recuperar la contraseña"
}
```


### Reprogramar cita

```http
  PUT /api/appointments/{appointment_id}/reschedule
```

#### Body

```javascript
{
  "new_date": "2025-05-05T15:00:00Z"
}


```
#### Response

```javascript
{
  "message": "Cita reprogramada"
}
```


### Cambiar contraseña actual

```http
  POST /api/auth/change-password
```

#### Body

```javascript
{
  "current_password": "passwordViejo",
  "new_password": "passwordNuevo"
}


```
#### Response

```javascript
{
  "message": "Contraseña actualizada"
}
```
