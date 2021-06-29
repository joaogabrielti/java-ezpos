SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `ezpos` DEFAULT CHARACTER SET utf8mb4;
USE `ezpos`;

DROP TABLE IF EXISTS `ezpos`.`produtos`;
CREATE TABLE IF NOT EXISTS `ezpos`.`produtos` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `descricao` VARCHAR(100) NULL,
  `quantidade` FLOAT NOT NULL DEFAULT 0,
  `valor_compra` FLOAT NOT NULL,
  `valor_venda` FLOAT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `ezpos`.`fornecedores`;
CREATE TABLE IF NOT EXISTS `ezpos`.`fornecedores` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cpf_cnpj` VARCHAR(20) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `endereco` VARCHAR(100) NULL,
  `telefone` VARCHAR(100) NULL,
  `email` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cpf_cnpj_UNIQUE` (`cpf_cnpj` ASC))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `ezpos`.`usuarios`;
CREATE TABLE IF NOT EXISTS `ezpos`.`usuarios` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `usuario` VARCHAR(16) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `senha` CHAR(64) NOT NULL,
  `admin` TINYINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `ezpos`.`compras`;
CREATE TABLE IF NOT EXISTS `ezpos`.`compras` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `fornecedor_id` INT UNSIGNED NOT NULL,
  `usuario_id` INT UNSIGNED NOT NULL,
  `valor` FLOAT NOT NULL,
  `data` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_compras_fornecedores_idx` (`fornecedor_id` ASC),
  INDEX `fk_compras_usuarios_idx` (`usuario_id` ASC),
  CONSTRAINT `fk_compras_fornecedores`
    FOREIGN KEY (`fornecedor_id`)
    REFERENCES `ezpos`.`fornecedores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_compras_usuarios`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `ezpos`.`usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `ezpos`.`compra_itens`;
CREATE TABLE IF NOT EXISTS `ezpos`.`compra_itens` (
  `compra_id` INT UNSIGNED NOT NULL,
  `produto_id` INT UNSIGNED NOT NULL,
  `quantidade` FLOAT NOT NULL,
  `valor` FLOAT NOT NULL,
  PRIMARY KEY (`compra_id`, `produto_id`),
  INDEX `fk_compras_itens_has_produtos_idx` (`produto_id` ASC),
  INDEX `fk_compra_itens_has_compras_idx` (`compra_id` ASC),
  CONSTRAINT `fk_compra_itens_has_compras`
    FOREIGN KEY (`compra_id`)
    REFERENCES `ezpos`.`compras` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_compras_itens_has_produtos_idx`
    FOREIGN KEY (`produto_id`)
    REFERENCES `ezpos`.`produtos` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `ezpos`.`clientes`;
CREATE TABLE IF NOT EXISTS `ezpos`.`clientes` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cpf_cnpj` VARCHAR(20) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `endereco` VARCHAR(100) NULL,
  `telefone` VARCHAR(100) NULL,
  `email` VARCHAR(100) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `ezpos`.`vendas`;
CREATE TABLE IF NOT EXISTS `ezpos`.`vendas` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cliente_id` INT UNSIGNED NOT NULL,
  `usuario_id` INT UNSIGNED NOT NULL,
  `valor` FLOAT NOT NULL,
  `forma_pagamento` VARCHAR(45) NOT NULL,
  `data` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_vendas_clientes_idx` (`cliente_id` ASC),
  INDEX `fk_vendas_usuarios_idx` (`usuario_id` ASC),
  CONSTRAINT `fk_vendas_clientes`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `ezpos`.`clientes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vendas_usuarios`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `ezpos`.`usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `ezpos`.`venda_itens`;
CREATE TABLE IF NOT EXISTS `ezpos`.`venda_itens` (
  `venda_id` INT UNSIGNED NOT NULL,
  `produto_id` INT UNSIGNED NOT NULL,
  `quantidade` FLOAT NOT NULL,
  `valor` FLOAT NOT NULL,
  PRIMARY KEY (`venda_id`, `produto_id`),
  INDEX `fk_vendas_itens_has_produtos_idx` (`produto_id` ASC),
  INDEX `fk_vendas_itens_has_vendas_idx` (`venda_id` ASC),
  CONSTRAINT `fk_vendas_itens_has_vendas`
    FOREIGN KEY (`venda_id`)
    REFERENCES `ezpos`.`vendas` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vendas_itens_has_produtos`
    FOREIGN KEY (`produto_id`)
    REFERENCES `ezpos`.`produtos` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

START TRANSACTION;
USE `ezpos`;
INSERT INTO `ezpos`.`usuarios` (`id`, `usuario`, `nome`, `email`, `senha`, `admin`) VALUES (1, 'admin', 'ADMINISTRADOR', 'admin@ezpos.com.br', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 1);
COMMIT;

DELIMITER $$

DROP FUNCTION IF EXISTS fn_valor_total_venda $$
CREATE FUNCTION fn_valor_total_venda(venda INT) RETURNS FLOAT DETERMINISTIC
BEGIN
    DECLARE valor_total FLOAT;
    SELECT SUM(quantidade * valor) INTO valor_total FROM venda_itens WHERE venda_id = venda;
    RETURN valor_total;
END $$

DROP FUNCTION IF EXISTS fn_valor_total_compra $$
CREATE FUNCTION fn_valor_total_compra(compra INT) RETURNS FLOAT DETERMINISTIC
BEGIN
    DECLARE valor_total FLOAT;
    SELECT SUM(quantidade * valor) INTO valor_total FROM compra_itens WHERE compra_id = compra;
    RETURN valor_total;
END $$

DROP FUNCTION IF EXISTS fn_atualiza_estoque $$
CREATE FUNCTION fn_atualiza_estoque(produto INT) RETURNS FLOAT DETERMINISTIC
BEGIN
    DECLARE entradas FLOAT;
    DECLARE saidas FLOAT;
    SELECT COALESCE(SUM(quantidade), 0) INTO entradas FROM compra_itens WHERE produto_id=produto;
    SELECT COALESCE(SUM(quantidade), 0) INTO saidas FROM venda_itens WHERE produto_id=produto;
    UPDATE produtos SET quantidade = entradas - saidas WHERE id=produto;
    RETURN entradas - saidas;
END $$

DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS tgr_atualiza_compra_insert $$
CREATE TRIGGER tgr_atualiza_compra_insert AFTER INSERT ON compra_itens FOR EACH ROW
BEGIN
    UPDATE produtos AS p
    SET p.quantidade = p.quantidade + new.quantidade
    WHERE p.id = new.produto_id;

    UPDATE compras
    SET valor = (SELECT fn_valor_total_compra(new.compra_id))
    WHERE id = new.compra_id;
END $$

DROP TRIGGER IF EXISTS tgr_atualiza_compra_update $$
CREATE TRIGGER tgr_atualiza_compra_update AFTER UPDATE ON compra_itens FOR EACH ROW
BEGIN
    UPDATE produtos AS p
    SET p.quantidade = p.quantidade - old.quantidade
    WHERE p.id = old.produto_id;

    UPDATE produtos AS p
    SET p.quantidade = p.quantidade + new.quantidade
    WHERE p.id = new.produto_id;

    UPDATE compras
    SET valor = (SELECT fn_valor_total_compra(old.compra_id))
    WHERE id = old.compra_id;

    UPDATE compras
    SET valor = (SELECT fn_valor_total_compra(new.compra_id))
    WHERE id = new.compra_id;
END $$

DROP TRIGGER IF EXISTS tgr_atualiza_compra_delete $$
CREATE TRIGGER tgr_atualiza_compra_delete AFTER DELETE ON compra_itens FOR EACH ROW
BEGIN
    UPDATE produtos AS p
    SET p.quantidade = p.quantidade - old.quantidade
    WHERE p.id = old.produto_id;

    UPDATE compras
    SET valor = (SELECT fn_valor_total_compra(old.compra_id))
    WHERE id = old.compra_id;
END $$

DROP TRIGGER IF EXISTS tgr_atualiza_venda_insert $$
CREATE TRIGGER tgr_atualiza_venda_insert AFTER INSERT ON venda_itens FOR EACH ROW
BEGIN
    UPDATE produtos AS p
    SET p.quantidade = p.quantidade - new.quantidade
    WHERE p.id = new.produto_id;

    UPDATE vendas
    SET valor = (SELECT fn_valor_total_venda(new.venda_id))
    WHERE id = new.venda_id;
END $$

DROP TRIGGER IF EXISTS tgr_atualiza_venda_update $$
CREATE TRIGGER tgr_atualiza_venda_update AFTER UPDATE ON venda_itens FOR EACH ROW
BEGIN
    UPDATE produtos AS p
    SET p.quantidade = p.quantidade + old.quantidade
    WHERE p.id = old.produto_id;

    UPDATE produtos AS p
    SET p.quantidade = p.quantidade - new.quantidade
    WHERE p.id = new.produto_id;

    UPDATE vendas
    SET valor = (SELECT fn_valor_total_venda(old.venda_id))
    WHERE id = old.venda_id;

    UPDATE vendas
    SET valor = (SELECT fn_valor_total_venda(new.venda_id))
    WHERE id = new.venda_id;
END $$

DROP TRIGGER IF EXISTS tgr_atualiza_venda_delete $$
CREATE TRIGGER tgr_atualiza_venda_delete AFTER DELETE ON venda_itens FOR EACH ROW
BEGIN
    UPDATE produtos AS p
    SET p.quantidade = p.quantidade + old.quantidade
    WHERE p.id = old.produto_id;

    UPDATE vendas
    SET valor = (SELECT fn_valor_total_venda(old.venda_id))
    WHERE id = old.venda_id;
END $$

DELIMITER ;

DELIMITER $$

DROP PROCEDURE IF EXISTS proc_gastos_totais $$
CREATE PROCEDURE proc_gastos_totais(IN cliente INT)
BEGIN
    SELECT
        c.id AS codigo,
        c.nome AS nome,
        c.cpf_cnpj AS cpf_cnpj,
        COUNT(v.cliente_id) AS total_compras,
        SUM(v.valor) AS total_gasto
    FROM clientes AS c, vendas AS v
    WHERE c.id = cliente AND v.cliente_id = cliente;
END $$

DROP PROCEDURE IF EXISTS proc_lucro_produto $$
CREATE PROCEDURE proc_lucro_produto(IN produto INT)
BEGIN
    DECLARE existe_mais_Linhas INT DEFAULT 0;
    DECLARE total_compras FLOAT DEFAULT 0;
    DECLARE total_vendas FLOAT DEFAULT 0;
    DECLARE lucro FLOAT DEFAULT 0;

    DECLARE quantidade INT DEFAULT 0;
    DECLARE valor FLOAT DEFAULT 0;

    DECLARE compras_cursor CURSOR FOR SELECT c.quantidade, c.valor FROM compra_itens AS c WHERE c.produto_id = produto;
    DECLARE vendas_cursor CURSOR FOR SELECT v.quantidade, v.valor FROM venda_itens AS v WHERE v.produto_id = produto;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET existe_mais_Linhas = 1;

    OPEN compras_cursor;
    compras_loop: LOOP
        FETCH compras_cursor INTO quantidade, valor;

        IF existe_mais_linhas = 1 THEN
            LEAVE compras_loop;
        END IF;
        
        SET total_compras = total_compras + (quantidade * valor);
    END LOOP compras_loop;
    CLOSE compras_cursor;

    SET existe_mais_Linhas = 0;

    OPEN vendas_cursor;
    vendas_loop: LOOP
        FETCH vendas_cursor INTO quantidade, valor;

        IF existe_mais_linhas = 1 THEN
            LEAVE vendas_loop;
        END IF;
        
        SET total_vendas = total_vendas + (quantidade * valor);
    END LOOP vendas_loop;
    CLOSE vendas_cursor;

    SET lucro = total_vendas - total_compras;

    SELECT produto, total_compras, total_vendas, lucro;
END $$

DROP PROCEDURE IF EXISTS proc_produto_favorito $$
CREATE PROCEDURE proc_produto_favorito(IN cliente INT)
BEGIN
    DECLARE existe_mais_Linhas INT DEFAULT 0;
    DECLARE maior_quantidade FLOAT DEFAULT 0;
    DECLARE maior_id INT DEFAULT 0;
    DECLARE quantidade FLOAT DEFAULT 0;
    DECLARE id INT DEFAULT 0;

    DECLARE _cursor CURSOR FOR
    SELECT v.produto_id, v.quantidade
    FROM venda_itens AS v
    WHERE v.venda_id IN (
        SELECT vnd.id FROM vendas AS vnd WHERE vnd.cliente_id = cliente
    );

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET existe_mais_Linhas = 1;

    OPEN _cursor;
    _loop: LOOP
        FETCH _cursor INTO id, quantidade;

        IF existe_mais_linhas = 1 THEN
            LEAVE _loop;
        END IF;

        IF quantidade >= maior_quantidade THEN
            SET maior_quantidade = quantidade;
            SET maior_id = id;
        END IF;
    END LOOP _loop;
    CLOSE _cursor;

    SELECT p.id AS id, p.nome AS nome FROM produtos AS p WHERE p.id = maior_id;
END $$

DELIMITER ;