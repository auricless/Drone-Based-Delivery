INSERT INTO drone (serial_number, model, weight_limit, battery, state) VALUES
('DRONE001', 'LIGHT_WEIGHT', 100, 100.0, 'IDLE'),
('DRONE002', 'MIDDLE_WEIGHT', 300, 95.5, 'LOADING'),
('DRONE003', 'CRUISER_WEIGHT', 500, 80.0, 'LOADED'),
('DRONE004', 'HEAVY_WEIGHT', 1000, 70.2, 'DELIVERING'),
('DRONE005', 'LIGHT_WEIGHT', 100, 60.8, 'DELIVERED'),
('DRONE006', 'MIDDLE_WEIGHT', 300, 50.0, 'RETURNING'),
('DRONE007', 'CRUISER_WEIGHT', 500, 85.5, 'IDLE'),
('DRONE008', 'HEAVY_WEIGHT', 1000, 90.0, 'LOADING'),
('DRONE009', 'LIGHT_WEIGHT', 100, 75.3, 'LOADED'),
('DRONE010', 'MIDDLE_WEIGHT', 300, 65.7, 'DELIVERING');

INSERT INTO medication (name, weight, code, image) VALUES
('Aspirin', 50, 'MED_001', '/images/aspirin.jpg'),
('Ibuprofen', 75, 'MED_002', '/images/ibuprofen.jpg'),
('Amoxicillin', 100, 'MED_003', '/images/amoxicillin.jpg'),
('Lisinopril', 60, 'MED_004', '/images/lisinopril.jpg'),
('Levothyroxine', 40, 'MED_005', '/images/levothyroxine.jpg'),
('Metformin', 80, 'MED_006', '/images/metformin.jpg'),
('Amlodipine', 55, 'MED_007', '/images/amlodipine.jpg'),
('Metoprolol', 70, 'MED_008', '/images/metoprolol.jpg'),
('Omeprazole', 65, 'MED_009', '/images/omeprazole.jpg'),
('Gabapentin', 90, 'MED_010', '/images/gabapentin.jpg');

INSERT INTO drone_medications (drone_id, medications_id) VALUES
(3, 1), (3, 2), (3, 3),
(4, 4), (4, 5),
(5, 6), (5, 7), (5, 8),
(9, 9), (9, 10);