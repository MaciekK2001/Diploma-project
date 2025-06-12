CREATE EXTENSION IF NOT EXISTS "pgcrypto";

DO
$$

BEGIN
INSERT INTO users (user_id, first_name, last_name, about, email, role, username)
VALUES
    ('6c84fb95-12c4-11ec-82a8-0242ac130001', 'John', 'Doe', 'Lorem ipsum dolor sit amet', 'john@example.com', 'USER', 'john2012'),
    ('6c84fb95-12c4-11ec-82a8-0242ac130002', 'Alice', 'Smith', 'Consectetur adipiscing elit', 'alice@example.com', 'USER', 'alice3312'),
    ('6c84fb95-12c4-11ec-82a8-0242ac130003', 'Michael', 'Johnson', 'Sed do eiusmod tempor incididunt', 'michael@example.com', 'USER', 'michael2210'),
    ('6c84fb95-12c4-11ec-82a8-0242ac130004', 'Emily', 'Brown', NULL, 'emily@example.com', 'USER', 'emily1'),
    ('6c84fb95-12c4-11ec-82a8-0242ac130005', 'David', 'Martinez', 'Ut labore et dolore magna aliqua', 'david@example.com', 'USER', 'david1');

INSERT INTO activity (activity_id, user_id, burnt_calories, time, created_at, activity_type)
VALUES
    ('6c84fbad-12c4-11ec-82a8-0242ac130001', '6c84fb95-12c4-11ec-82a8-0242ac130001', 500, 360000, CURRENT_TIMESTAMP, 'WORKOUT'),
    ('6c84fbad-12c4-11ec-82a8-0242ac130002', '6c84fb95-12c4-11ec-82a8-0242ac130002', 300, 180000, CURRENT_TIMESTAMP, 'RUN'),
    ('6c84fbad-12c4-11ec-82a8-0242ac130003', '6c84fb95-12c4-11ec-82a8-0242ac130003', 600, 450000, CURRENT_TIMESTAMP, 'SWIM'),
    ('6c84fbad-12c4-11ec-82a8-0242ac130004', '6c84fb95-12c4-11ec-82a8-0242ac130004', 400, 270000, CURRENT_TIMESTAMP, 'DANCE'),
    ('6c84fbad-12c4-11ec-82a8-0242ac130005', '6c84fb95-12c4-11ec-82a8-0242ac130005', 700, 540000, CURRENT_TIMESTAMP, 'TEAM_SPORT'),
    ('6c84fbad-12c4-11ec-82a8-0242ac130006', '6c84fb95-12c4-11ec-82a8-0242ac130001', 700, 540000, '2024-03-28 00:00:00'::timestamp, 'WORKOUT');
FOR i IN 1..50 LOOP
        INSERT INTO activity (activity_id, user_id, burnt_calories, time, created_at, activity_type)
        VALUES (
            gen_random_uuid(),
            '6c84fb95-12c4-11ec-82a8-0242ac130001',
            (300 + i * 10),
            (180000 + i * 10000),
            CURRENT_TIMESTAMP - (i || ' days')::interval,
            CASE i % 5
                WHEN 0 THEN 'WORKOUT'
                WHEN 1 THEN 'RUN'
                WHEN 2 THEN 'SWIM'
                WHEN 3 THEN 'DANCE'
                ELSE 'TEAM_SPORT'
            END
        );
END LOOP;
END
$$;
