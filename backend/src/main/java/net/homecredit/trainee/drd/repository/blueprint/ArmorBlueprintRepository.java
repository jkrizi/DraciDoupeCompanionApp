package net.homecredit.trainee.drd.repository.blueprint;

import net.homecredit.trainee.drd.entity.blueprint.item.ArmorBlueprint;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Repository
public class ArmorBlueprintRepository {

    private EntityManager entityManager;

    public ArmorBlueprintRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(ArmorBlueprint armorBlueprint) {
        entityManager.persist(armorBlueprint);
    }

    public ArmorBlueprint find(UUID id) {
        ArmorBlueprint result = entityManager.createQuery(
                "SELECT x FROM ArmorBlueprint x WHERE x.id = ?1", ArmorBlueprint.class)
                .setParameter(1, id).getSingleResult();
        return result;
    }

    public void deleteAll() {
        for (ArmorBlueprint armorBlueprint : findAll()) {
            entityManager.remove(armorBlueprint);
        }
    }

    public List<ArmorBlueprint> findAll() {
        TypedQuery<ArmorBlueprint> query = entityManager.createQuery("select x from ArmorBlueprint x", ArmorBlueprint.class);
        return query.getResultList();
    }
}
