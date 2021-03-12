insert into hospital (id, address, name) values (nextval('hospital_seq'), 'Kremlevskaya 12', 'Hospinal 7');
insert into humans (id, firstname, lastname, policy) values (nextval('humans_seq'), 'Alexandr', 'Pushkin', '244624');
insert into diagnoses (id, date, name, patient_id) values (nextval('diagnosed_seq'), '12.01.2021', 'flu', nextval('humans_seq'));
