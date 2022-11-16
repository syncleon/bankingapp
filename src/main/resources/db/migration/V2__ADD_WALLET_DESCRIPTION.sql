ALTER TABLE wallets ADD COLUMN description VARCHAR(255);
UPDATE wallets set description='wallet description'