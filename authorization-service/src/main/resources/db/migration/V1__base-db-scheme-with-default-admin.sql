create TABLE IF NOT EXISTS users(
    id uuid DEFAULT gen_random_uuid(),
    email varchar(511) NOT NULL,
    password varchar(511) NOT NULL,
    role varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

insert into users(id, email, password, role)
values (gen_random_uuid(), 'admin@admin.com', '$2a$06$eE3vVx0Ev1n4ZmnE8N6AX.uy2cSgRAbmITEUUKOmKObgvUzXO9HoG', 'ADMIN')