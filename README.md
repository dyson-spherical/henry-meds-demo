# Henry Meds Challenge Submission

## How to run this app
1. Install Java 17 on your machine
2. Run `./gradlew bootRun` to start the service

I recommend running these curl commands in this order to test the system
```curl
# Create a provider
curl -X POST --location "http://localhost:8080/providers" \
    -H "Content-Type: application/json" \
    -d '{
          "name": "Frank"
        }'
# Capture resulting Provider UUID

curl -X POST --location "http://localhost:8080/availability/{{UUID from above}}/available" \
    -H "Content-Type: application/json" \
    -d '{
          "startTime": "2023-12-10T10:00:00",
          "endTime": "2023-12-10T16:00:00"
        }'

# Verify that provider created correctly (and get UUID if needed)
curl -X GET --location "http://localhost:8080/providers?pageSize=10&start=0"

# Create a client
curl -X POST --location "http://localhost:8080/clients" \
    -H "Content-Type: application/json" \
    -d '{
          "name": "Frank-n-furter"
        }'
# Capture Client UUID
        
# Create an appointment (will succeed as I don't currently validate open slots
curl -X POST --location "http://localhost:8080/appointments" \
    -H "Content-Type: application/json" \
    -d '{
          "startTime": "2023-12-09T10:00:00",
          "endTime": "2023-12-09T10:00:00",
          "requestedProvider": "{{ PROVIDER UUID }}",
          "scheduledBy": "{{ CLIENT UUID }}",
          "status": "OPEN"
        }'
# Capture resulting UUID for later to confirm or cancel

# Scan for available time slots (has a bug where all results are the last slot of the day... :()
curl -X GET --location "http://localhost:8080/availability/availableSlots?requestedDay=2023-12-10T00%3A00%3A00Z"

```