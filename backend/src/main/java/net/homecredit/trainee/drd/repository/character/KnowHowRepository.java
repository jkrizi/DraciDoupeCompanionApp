package net.homecredit.trainee.repository.character;

import net.homecredit.trainee.entity.character.profession.ProfessionKnowHow;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

public class KnowHowRepository {

    private EntityManager entityManager;

    public KnowHowRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void saveKnowHow(ProfessionKnowHow professionKnowHow) {
        entityManager.persist(professionKnowHow);
    }
}
