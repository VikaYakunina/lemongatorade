create sequence hospital_seq start 1 increment 1;
create sequence diagnosed_seq start 1 increment 1;
create sequence humans_seq start 1 increment 1;

create table diagnoses (
    id int8 not null,
    date text ,
    name text,
    patient_id int8
);
create table hospital (
    id int8 not null,
    address text,
    name text
);

create table humans (
    id int8 not null,
    firstname text,
    lastname text,
    policy text
);

create table treatments (
    id int8 not null,
    name varchar(255),
    reception varchar(255)
);

