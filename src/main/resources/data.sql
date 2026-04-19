INSERT INTO devices (name, model_number, serial_number, introduction_date, location, status)
VALUES ('MacBook Pro', 'A2918', 'CY2XG123ABGD', '2025-10-01', '4Fデジタル室', 'AVAILABLE');

INSERT INTO devices (name, model_number, serial_number, introduction_date, location, status)
VALUES ('iPad', 'GF847hk', 'DNPX98765432', '2026-01-15', '3F会議室横', 'RENTED');

INSERT INTO devices (name, model_number, serial_number, introduction_date, location, status)
VALUES ('モニター', 'U2723QE', '7S12345', '2024-05-20', '備品管理棚', 'MAINTENANCE');


-- 現在（2026年4月）を中心としたテストデータ
INSERT INTO reservation (device_id, user_id, start_date, end_date) VALUES
-- パターン1: 今月内の短い予約
(1, 1, '2026-04-01', '2026-04-03'),

-- パターン2: 1週間程度の予約
(1, 2, '2026-04-10', '2026-04-15'),

-- パターン3: 月をまたぐ予約（4月末〜5月頭）
-- これで月移動の表示確認ができます
(1, 3, '2026-04-28', '2026-05-02'),

-- パターン4: 別の端末（device_id=2）の予約
(2, 1, '2026-04-15', '2026-04-20'),

-- パターン5: 少し先の予約
(1, 2, '2026-05-10', '2026-05-15');


--password1234
INSERT INTO users (username, password, authority) VALUES ('ami', '6cbc564704c412284776c751c822687be584ab3f1dd6a2db42a83a53caac9b4cdd519aa8ea0215368d37c70d8b9a69c9', 'ADMIN');
INSERT INTO users (username, password, authority) VALUES ('ken', '00c21f72487eca57494132fff53e551c017a999830f74ad72e557979c9df4e7e2ada79882c783b413fd0180034e31bb2', 'USER');
INSERT INTO users (username, password, authority) VALUES ('yuki', '046662014407edf2630e3c34d4249dfa9e74589a6fb8394724f0bf45eb5d00e60f9a566e52e5fdc272171b2d4861dfb8', 'ADMIN');
