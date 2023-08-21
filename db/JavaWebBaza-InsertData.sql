-- noinspection SqlNoDataSourceInspectionForFile

use JavaWebBaza
go

-- Categories
insert into [Kategorija]([Naziv]) values
('CPU'),
('Motherboard'),
('GPU');

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