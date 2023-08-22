-- noinspection SqlNoDataSourceInspectionForFile

----------------------------------------------------------------------------------------------------------------------------------------------------------

use JavaWebBaza
go

----------------------------------------------------------------------------------------------------------------------------------------------------------

-- Categories
insert into [Kategorija]([Naziv]) values
('CPU'),
('Motherboard'),
('GPU');

----------------------------------------------------------------------------------------------------------------------------------------------------------

-- Products
-- CPUs
insert into [Proizvod]([KategorijaID], [Naziv], [Cijena], [DostupnaKolicina]) values
(1, 'AMD Ryzen 9 5950X', 799.99, 100),
(1, 'Intel Core i9-10900K', 499.99, 120);

-- Motherboards
insert into [Proizvod]([KategorijaID], [Naziv], [Cijena], [DostupnaKolicina]) values
(2, 'MSI MPG B550 GAMING PLUS', 149.99, 75),
(2, 'ASUS ROG Strix B450-F Gaming', 129.99, 80);

-- GPUs
insert into [Proizvod]([KategorijaID], [Naziv], [Cijena], [DostupnaKolicina]) values
(3, 'NVIDIA GeForce RTX 3080', 699.99, 50),
(3, 'AMD Radeon RX 6900 XT', 999.99, 40);

----------------------------------------------------------------------------------------------------------------------------------------------------------

-- Users
insert into [users](username, password, enabled) values
('user', '$2a$10$2L.FudeEOgf80dFJ7Q9NXu01moIMpDaa7a5RCCwjFvH8RQXwA6sz2', 1),
('admin', '$2a$10$2L.FudeEOgf80dFJ7Q9NXu01moIMpDaa7a5RCCwjFvH8RQXwA6sz2', 1);

-- Authorities
insert into [authorities](username, authority) values
('user', 'ROLE_USER'),
('admin', 'ROLE_ADMIN');

----------------------------------------------------------------------------------------------------------------------------------------------------------