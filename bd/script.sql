-- SQLBook: Code
CREATE DATABASE fastTotem;
USE fastTotem;

CREATE TABLE Endereco (
	idEndereco INT PRIMARY KEY AUTO_INCREMENT,
	logradouro VARCHAR(255),
	bairro VARCHAR(255),
	numero CHAR(7),
	complemento VARCHAR(255),
	cep VARCHAR(15)
);

CREATE TABLE Empresa(
	idEmpresa INT PRIMARY KEY AUTO_INCREMENT,
	razaoSocial VARCHAR(120),
	cnpj CHAR(14),
	email VARCHAR(255),
    fkEndereco INT,
	FOREIGN KEY (fkEndereco) REFERENCES Endereco(idEndereco)
);

CREATE TABLE Usuario (
	idUsuario INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(255),
	email VARCHAR(255),
	senha VARCHAR(255),
	nivelAcesso ENUM('Administrador','Funcionário'),
	imgUsuario TEXT(255),
	fkEmpresa INT,
	FOREIGN KEY (fkEmpresa) REFERENCES Empresa(idEmpresa)
);

CREATE TABLE Totem(
	idTotem INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(255),
	chaveDeAcesso VARCHAR(255),
    boardSerialNumber VARCHAR(255),
	fkEmpresa INT,
	FOREIGN KEY (fkEmpresa) REFERENCES Empresa(idEmpresa)
);

CREATE TABLE InfoMaquina(
	idInfoMaquina INT PRIMARY KEY AUTO_INCREMENT,
	sistemaOperacional VARCHAR(200),
	fabricante  VARCHAR(200),
	nomeProcessador VARCHAR(200),
	capacidadeRam DOUBLE,
	capacidadeDisco DOUBLE,
	fkTotem INT,
	FOREIGN KEY (fkTotem) REFERENCES Totem(idTotem)
);

CREATE TABLE Componente(
	idComponente INT PRIMARY KEY AUTO_INCREMENT,
	nomeComponente VARCHAR(255),
    tipoComponente VARCHAR(255),
	fkTotem INT,
	FOREIGN KEY (fkTotem) REFERENCES Totem(idTotem)
);

CREATE TABLE Captura (
	idCaptura INT PRIMARY KEY AUTO_INCREMENT,
	dataHora DATETIME,
	tipo VARCHAR(255),
	valor DOUBLE,
	fkComponente INT,
    fkTotem INT,
	FOREIGN KEY (fkComponente) REFERENCES Componente(idComponente),
	FOREIGN KEY (fkTotem) REFERENCES Totem(idTotem)
);

CREATE TABLE ParametroAlerta(
	idParametroAlerta INT PRIMARY KEY AUTO_INCREMENT,
	fkComponente INT,
	ideal INT,
	alerta INT,
	critico INT,
	notificacao INT,
	FOREIGN KEY (fkComponente) REFERENCES Componente (idComponente)
); 