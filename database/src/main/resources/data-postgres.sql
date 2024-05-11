DO
$$

BEGIN
-- Wstawianie przykładowych danych do tabeli app_user
INSERT INTO app_user (app_user_id, first_name, last_name, about,  email)
VALUES
    ('6c84fb95-12c4-11ec-82a8-0242ac130001', 'John', 'Doe', 'Lorem ipsum dolor sit amet', 'john@example.com'),
    ('6c84fb95-12c4-11ec-82a8-0242ac130002', 'Alice', 'Smith', 'Consectetur adipiscing elit', 'alice@example.com'),
    ('6c84fb95-12c4-11ec-82a8-0242ac130003', 'Michael', 'Johnson', 'Sed do eiusmod tempor incididunt', 'michael@example.com'),
    ('6c84fb95-12c4-11ec-82a8-0242ac130004', 'Emily', 'Brown', NULL, 'emily@example.com'),
    ('6c84fb95-12c4-11ec-82a8-0242ac130005', 'David', 'Martinez', 'Ut labore et dolore magna aliqua', 'david@example.com');

-- Wstawianie przykładowych danych do tabeli activity
INSERT INTO activity (activity_id, app_user_id, burnt_calories, time, created_at, type)
VALUES
    ('6c84fbad-12c4-11ec-82a8-0242ac130001', '6c84fb95-12c4-11ec-82a8-0242ac130001', 500, 360000, CURRENT_TIMESTAMP, 'WORKOUT'),
    ('6c84fbad-12c4-11ec-82a8-0242ac130002', '6c84fb95-12c4-11ec-82a8-0242ac130002', 300, 180000, CURRENT_TIMESTAMP, 'RUN'),
    ('6c84fbad-12c4-11ec-82a8-0242ac130003', '6c84fb95-12c4-11ec-82a8-0242ac130003', 600, 450000, CURRENT_TIMESTAMP, 'SWIM'),
    ('6c84fbad-12c4-11ec-82a8-0242ac130004', '6c84fb95-12c4-11ec-82a8-0242ac130004', 400, 270000, CURRENT_TIMESTAMP, 'DANCE'),
    ('6c84fbad-12c4-11ec-82a8-0242ac130005', '6c84fb95-12c4-11ec-82a8-0242ac130005', 700, 540000, CURRENT_TIMESTAMP, 'TEAM_SPORT'),
    ('6c84fbad-12c4-11ec-82a8-0242ac130006', '6c84fb95-12c4-11ec-82a8-0242ac130001', 700, 540000, '2024-03-28 00:00:00'::timestamp, 'WORKOUT');

END
$$;

--SELECT AVG(Activity.burnt_calories) FROM Activity
--WHERE Activity.app_user_id = '6c84fb95-12c4-11ec-82a8-0242ac130001'
--AND Activity.created_at >= NOW() - INTERVAL '7' DAY