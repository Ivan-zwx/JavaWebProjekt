-- noinspection SqlNoDataSourceInspectionForFile

----------------------------------------------------------------------------------------------------------------------------------------------------------
-- JavaWebBaza - Database Creation
----------------------------------------------------------------------------------------------------------------------------------------------------------
-- DB

use master
go

if db_id('JavaWebBaza') is not null
begin
	alter database JavaWebBaza
	set single_user
	with rollback immediate
	drop database JavaWebBaza
end

----------------------------------------------------------------------------------------------------------------------------------------------------------
-- DB

create database JavaWebBaza
go

alter database JavaWebBaza
set single_user
with rollback immediate

use JavaWebBaza
go

----------------------------------------------------------------------------------------------------------------------------------------------------------
-- Entities

create table [users]
(
	[username] nvarchar(300) primary key,
	[password] nvarchar(300),
	[enabled] bit
)

create table [authorities]
(
	[username] nvarchar(300) foreign key references [users]([username]),
	[authority] nvarchar(300)
)

create table [Kategorija]
(
	[IDKategorija] int primary key identity,
	[Naziv] nvarchar(300)
)

create table [Proizvod]
(
	[IDProizvod] int primary key identity,
	[KategorijaID] int foreign key references [Kategorija]([IDKategorija]),
	[Naziv] nvarchar(300),
	[Cijena] float,
	[DostupnaKolicina] int
)

create table [Racun]
(
	[IDRacun] int primary key identity,
	[username] nvarchar(300) foreign key references [users]([username]),
	[VrijemeKupovine] nvarchar(300),
	[NacinKupovine] nvarchar(300),
	[UkupnaCijena] float
)

create table [Stavka]
(
	[IDStavka] int primary key identity,
	[RacunID] int foreign key references [Racun]([IDRacun]),
	[ProizvodID] int foreign key references [Proizvod]([IDProizvod]),
	[Kolicina] int
)

create table [LoginHistory]
(
	[IDLoginHistory] int primary key identity,
	[username] nvarchar(300),
	[VrijemeLogina] nvarchar(300),
	[IPAdresa] nvarchar(300)
)

create table [RequestHistory]
(
	[IDRequestHistory] int primary key identity,
	[username] nvarchar(300),
	[VrijemeRequesta] nvarchar(300),
	[Request] nvarchar(3000)
)

----------------------------------------------------------------------------------------------------------------------------------------------------------
-- DB

alter database JavaWebBaza
set multi_user

----------------------------------------------------------------------------------------------------------------------------------------------------------