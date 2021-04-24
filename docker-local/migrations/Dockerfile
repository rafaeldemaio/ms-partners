FROM python:3.6-alpine

WORKDIR /code
COPY . .

RUN pip install --no-cache-dir -r requirements.txt

CMD [ "python", "migration.py" ]
