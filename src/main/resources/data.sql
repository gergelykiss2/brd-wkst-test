INSERT INTO CAR
VALUES (1, 'SUF131', 'Ford', 'Focus', 'DIESEL', 'AVAILABLE', NOW(), NOW()),
       (2, 'SUF132', 'Ford', 'Focus', 'DIESEL', 'AVAILABLE', NOW(), NOW()),
       (3, 'SUF133', 'Ford', 'Focus', 'HYBRID', 'IN_SERVICE', NOW(), NOW()),
       (4, 'SBA345', 'Ford', 'Focus Combi', 'DIESEL', 'AVAILABLE', NOW(), NOW()),
       (5, 'SBA345', 'Ford', 'Focus Combi', 'DIESEL', 'AVAILABLE', NOW(), NOW());

INSERT INTO USER
VALUES (1, 'Teszt', 'Elek', 'teszt.elek@example.com', '1980-01-01', NULL, NULL, 1, NOW(), NOW()),
       (2, 'Teszt', 'Elekne', 'teszt.elekne@example.com', '1982-01-01', NULL, NULL, 1, NOW(), NOW());

INSERT INTO RESERVATION
VALUES (1, '2021-10-01', '2021-10-02', 'BOOKED', 'WORK', 1, 1, NOW(), NOW()),
       (2, '2021-12-24', '2021-12-28', 'CANCELLED', 'PERSONAL', 2, 5, NOW(), NOW());

INSERT INTO EQUIPMENT
VALUES (1, 'Climate Control', NOW(), NOW()),
       (2, 'Cruise Control', NOW(), NOW()),
       (3, 'Navigation', NOW(), NOW()),
       (4, 'Rain sensor', NOW(), NOW()),
       (5, 'Blind spot detection', NOW(), NOW());

INSERT INTO CAR_EQUIPMENT
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (4, 3),
       (5, 3),
       (4, 4),
       (5, 4),
       (4, 5),
       (5, 5),
       (1, 4),
       (2, 4),
       (3, 4);