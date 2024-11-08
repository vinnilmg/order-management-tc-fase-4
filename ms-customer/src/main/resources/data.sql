INSERT INTO addresses
(STREET, NUMBER, COMPLEMENT, DISTRICT, CITY, STATE, CEP)
VALUES
    ('Rua das Fiandeiras', '20', null, 'Vila Olímpia', 'São Paulo', 'SP', '04545005'),
    ('Rua Alecrim', '182', null, 'Jardim Vila Rica', 'Passos', 'MG', '37901224');

INSERT INTO customers
(CPF, NAME, ADDRESS_ID, BIRTH_DATE)
VALUES
    ('06463241016', 'John Smith', 1, '2006-02-16'),
    ('46799709013', 'Peter Capaldi', 2, '1958-04-14');



