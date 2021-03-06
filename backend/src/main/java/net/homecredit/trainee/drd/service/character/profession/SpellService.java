package net.homecredit.trainee.drd.service.character.profession;

import net.homecredit.trainee.drd.entity.character.person.profession.wizard.Spell;
import net.homecredit.trainee.drd.repository.character.SpellRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class SpellService {

    private SpellRepository spellRepository;

    public SpellService(SpellRepository spellRepository) {
        this.spellRepository = spellRepository;
    }

    public List<Spell> findAll() {
        return spellRepository.findAll();
    }

    public void save(Spell newSpell) {
        if(spellRepository.containsSpell(newSpell)){
            throw new RuntimeException("Spell with the same name already exists");
        }
        spellRepository.save(newSpell);
    }

    public void delete(UUID id) {
        spellRepository.delete(id);
    }

    public void update(Spell existingSpell) {
        spellRepository.update(existingSpell);
    }
}
