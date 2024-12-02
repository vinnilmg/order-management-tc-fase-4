INSERT INTO shipping
(region, cep_start, cep_end, days_to_delivery, delivery_price)
VALUES
('SUDESTE', '01000000', '39999999', '3', 10.0),
('SUL', '80000000', '99999999', '5', 15.0),
('NORDESTE', '40000000', '65999999', '20', 50.0),
('NORTE', '66000000', '77999999', '15', 25),
('CENTRO_OESTE', '70000000', '79999999', '10', 20.0);

INSERT INTO courier
    (name, region_id, status, phone)
VALUES
    ('JOSE', 1, 'AVAILABLE', '11985212021'),
    ('MARIA', 2, 'AVAILABLE', '11965201101'),
    ('CARLOS', 3, 'AVAILABLE', '11988771120'),
    ('ANA', 4, 'AVAILABLE', '11998524578'),
    ('PEDRO', 2, 'AVAILABLE', '1192102125');

INSERT INTO DELIVERY
    ( courier_id, order_id, status, region_id)
VALUES
    (1, 1, 'WAITING_FOR_DELIVERY', 1),
    (2, 2, 'ON_DELIVERY_ROUTE', 2),
    (3, 3, 'WAITING_FOR_DELIVERY', 3)
