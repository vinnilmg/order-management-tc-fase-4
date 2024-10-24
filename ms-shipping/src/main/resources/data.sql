INSERT INTO shipping
(region, cep_start, cep_end, days_to_delivery, delivery_price)
VALUES
('SUDESTE', '01000000', '39999999', '3', 10.0),
('SUL', '80000000', '99999999', '5', 15.0),
('NORDESTE', '40000000', '65999999', '20', 50.0),
('NORTE', '66000000', '77999999', '15', 25),
('CENTRO_OESTE', '70000000', '79999999', '10', 20.0);

INSERT INTO courier
    (name, region, status)
VALUES
    ('JOSE', 'SUDESTE', 'AVAILABLE'),
    ('MARIA', 'SUL', 'AVAILABLE'),
    ('CARLOS', 'NORDESTE', 'AVAILABLE'),
    ('ANA', 'NORTE', 'AVAILABLE'),
    ('PEDRO', 'CENTRO_OESTE', 'AVAILABLE');

INSERT INTO DELIVERY
    (latitude, longitude, courier_id, order_id, status)
VALUES
    ('0', '0', 1, 1, 'WAITING_FOR_DELIVERY'),
    ('1', '1', 2, 2, 'WAITING_FOR_DELIVERY'),
    ('2', '2', 3, 3, 'WAITING_FOR_DELIVERY'),
    ('3', '3', 4, 4, 'WAITING_FOR_DELIVERY'),
    ('4', '4', 5, 5, 'WAITING_FOR_DELIVERY');