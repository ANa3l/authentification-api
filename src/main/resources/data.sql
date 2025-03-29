-- Insertion de comptes de test cohérents dans la base "compte_db"
-- Les mots de passe sont hachés en SHA-256 (fictifs)

INSERT INTO compte (id, mot_de_passe) VALUES
('e22502314', 'e3afed0047b08059d0fada10f400c1e5'), -- motdepasse
('alice@example.com', '5e884898da28047151d0e56f8dc62927'), -- password
('e22345678', 'bcb2d4c3b0ee4ce0a2f97f9d8c89f7a5'), -- etudiant1
('bob@example.com', '12dada88dd2fc3137ac16a8d43d6e0aa'), -- test1234
('e22999999', 'f379eaf3c831b04de153469d1bec345e'); -- azerty
