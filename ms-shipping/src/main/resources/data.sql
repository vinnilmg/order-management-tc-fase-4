INSERT INTO shipping
(region, cep_start, cep_end, days_to_delivery, delivery_price)
VALUES
('SUDESTE', '01000000', '39999999', '3', 10.0),
('SUL', '80000000', '99999999', '5', 15.0),
('NORDESTE', '40000000', '65999999', '20', 50.0),
('NORTE', '66000000', '77999999', '15', 25),
('CENTRO_OESTE', '70000000', '79999999', '10', 20.0);

INSERT INTO courier
    (name, region, status, phone)
VALUES
    ('JOSE', 'SUDESTE', 'AVAILABLE', '11985212021'),
    ('MARIA', 'SUL', 'AVAILABLE', '11965201101'),
    ('CARLOS', 'NORDESTE', 'AVAILABLE', '11988771120'),
    ('ANA', 'NORTE', 'ON_DELIVERY_ROUTE', '11998524578'),
    ('PEDRO', 'SUL', 'AVAILABLE', '1192102125');

INSERT INTO DELIVERY
     ( order_id, status, region)
 VALUES
     ( 1, 'WAITING_FOR_DELIVERY', 'SUL'),
     ( 2, 'WAITING_FOR_DELIVERY', 'SUL'),
     ( 3, 'WAITING_FOR_DELIVERY', 'NORTE');