-- 1. CLEANUP (Ensures the script can run multiple times without errors)
DROP TRIGGER IF EXISTS trg_lowercase_email ON manzil_user; //
DROP TRIGGER IF EXISTS trg_update_user_timestamp ON manzil_user; //
DROP TRIGGER IF EXISTS trg_prevent_double_role ON registered_user; //

DROP FUNCTION IF EXISTS lowercase_email(); //
DROP FUNCTION IF EXISTS update_timestamp(); //
DROP FUNCTION IF EXISTS check_admin_exists(); //

-- 2. EMAIL NORMALIZER
CREATE FUNCTION lowercase_email()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.email = LOWER(NEW.email);
RETURN NEW;
END;
$$ LANGUAGE plpgsql; //

CREATE TRIGGER trg_lowercase_email
    BEFORE INSERT OR UPDATE ON manzil_user
                         FOR EACH ROW EXECUTE FUNCTION lowercase_email(); //

-- 3. LAST UPDATE SETTER
CREATE FUNCTION update_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.last_updated = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql; //

CREATE TRIGGER trg_update_user_timestamp
    BEFORE UPDATE ON manzil_user
    FOR EACH ROW EXECUTE FUNCTION update_timestamp(); //

-- 4. ROLE EXCLUSIVITY GUARD
CREATE FUNCTION check_admin_exists()
    RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM admin WHERE user_id = NEW.user_id) THEN
        RAISE EXCEPTION 'This user is already an Admin and cannot be a RegisteredUser';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql; //

CREATE TRIGGER trg_prevent_double_role
    BEFORE INSERT ON registered_user
    FOR EACH ROW EXECUTE FUNCTION check_admin_exists(); //