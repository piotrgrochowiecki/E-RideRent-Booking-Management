create table booking (
                         id bigint not null auto_increment,
                         car_uuid varchar(255),
                         end_date date,
                         start_date date,
                         user_uuid varchar(255),
                         primary key (id)
);