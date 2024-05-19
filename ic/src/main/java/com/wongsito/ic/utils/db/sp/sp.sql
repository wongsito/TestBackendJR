USE school;

-- SP que elimina todos los registros asociados a un estudiante
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_delete_registrations_per_user $$
CREATE PROCEDURE sp_delete_registrations_per_user(IN p_student_id BIGINT)
BEGIN
DELETE FROM registrations WHERE student_id = p_student_id;
END$$
DELIMITER ;
-- CALL sp_delete_registrations_per_user(1);

-- SP que elimina todos los registros asociados a un curso
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_delete_registrations_per_course $$
CREATE PROCEDURE sp_delete_registrations_per_course(IN p_course_id BIGINT)
BEGIN
DELETE FROM registrations WHERE course_id = p_course_id;
END$$
DELIMITER ;
-- CALL sp_delete_registrations_per_course(1);

-- SP que actualiza todos los registros asociados a un estudiante
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_update_registrations_per_user $$
CREATE PROCEDURE sp_update_registrations_per_user(IN p_course_id BIGINT, IN p_type_approval VARCHAR(20))
BEGIN
    DECLARE v_id BIGINT;
    DECLARE v_type_approval VARCHAR(20);

    -- Obtener el tipo de aprobación del curso dado
SELECT type_approval INTO v_type_approval
FROM courses
WHERE id = p_course_id;

-- Actualizar el estado de aprobación de los registros correspondientes al curso dado
UPDATE registrations
SET approved = CASE
                   WHEN v_type_approval = 'qualification' AND qualification >= 7.0 THEN TRUE
                   WHEN v_type_approval = 'assists' AND assists >= 80 THEN TRUE
                   WHEN v_type_approval = 'mixed' AND (qualification >= 7.0 AND assists >= 80) THEN TRUE
                   ELSE FALSE
    END
WHERE course_id = p_course_id;
END$$
DELIMITER ;
-- CALL sp_update_registrations_per_user(1, "mixed");