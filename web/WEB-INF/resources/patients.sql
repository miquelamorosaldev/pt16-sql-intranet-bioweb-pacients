/* CSV to SQL */
CREATE TABLE IF NOT EXISTS Patient(
   idRegistre      INTEGER  NOT NULL PRIMARY KEY 
  ,edat            INTEGER  NOT NULL
  ,grupEdat        VARCHAR(5) NOT NULL
  ,pes             INTEGER  NOT NULL
  ,talla           INTEGER  NOT NULL
  ,IMC             NUMERIC(5,2) NOT NULL
  ,classificació   VARCHAR(20) NOT NULL
  ,menarquia       INTEGER  NOT NULL
  ,menopausia      VARCHAR(2) NOT NULL
  ,tipusMenopausia VARCHAR(20) NOT NULL
);
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (1,57,'55–59',70,168,24.8,'OSTEOPENIA',12,'NO','NO CONSTA');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (2,46,'45–49',53,152,22.94,'OSTEOPENIA',13,'NO','NO CONSTA');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (3,46,'45–49',64,158,25.64,'NORMAL',14,'NO','NO CONSTA');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (4,53,'50–54',78,161,30.09,'OSTEOPENIA',10,'SI','NATURAL');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (5,46,'45–49',56,157,22.72,'NORMAL',13,'NO','NO CONSTA');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (6,45,'45–49',63,170,21.97,'OSTEOPOROSI',14,'NO','NO CONSTA');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (7,61,'60–64',83,170,28.17,'NORMAL',11,'NO','NATURAL');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (8,48,'45–49',70,168,24.80,'NORMAL',13,'NO','NATURAL');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (9,51,'50–54',64,158,25.64,'OSTEOPENIA',12,'SI','AMBDUES');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (10,51,'50–54',64,158,25.64,'NORMAL',11,'SI','OVARIECTOMIA');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (11,45,'45–49',63,170,21.97,'OSTEOPOROSI',14,'NO','NO CONSTA');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (12,61,'60–64',63,170,21.97,'NORMAL',12,'NO','NATURAL');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (13,68,'65–69',70,168,24.8,'OSTEOPENIA',15,'NO','NO CONSTA');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (14,43,'Men45',64,158,25.64,'OSTEOPENIA',12,'NO','NATURAL');
INSERT INTO Patient(idRegistre,edat,grupEdat,pes,talla,IMC,classificació,menarquia,menopausia,tipusMenopausia) VALUES (15,72,'Maj69',80,171,26.64,'OSTEOPOROSI',14,'NO','NO CONSTA');
