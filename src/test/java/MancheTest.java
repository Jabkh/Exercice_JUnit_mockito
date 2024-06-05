import org.example.IGenerateur;
import org.example.Manche;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MancheTest {

    private Manche manche;
    private Manche derniereManche;
    private final IGenerateur generateur = Mockito.mock(IGenerateur.class);

    @BeforeEach
    public void setUp() {
        manche = new Manche(generateur, false);
        derniereManche = new Manche(generateur, true);
    }

    @Test
    public void Rouleau_MancheSimple_PremierLancer_VerifierScore() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(5);

        // Act
        manche.lancer();

        // Assert
        assertEquals(5, manche.getScore());
    }

    @Test
    public void Rouleau_MancheSimple_DeuxiemeLancer_VerifierScore() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(3);

        // Act
        manche.lancer();
        manche.lancer();

        // Assert
        assertEquals(6, manche.getScore());
    }

    @Test
    public void Rouleau_MancheSimple_DeuxiemeLancer_PremierStrike_RetourneFaux() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(10);

        // Act
        manche.lancer();
        boolean result = manche.lancer();

        // Assert
        assertFalse(result);
    }

    @Test
    public void Rouleau_MancheSimple_PlusDeLancers_RetourneFaux() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(5);

        // Act
        manche.lancer();
        manche.lancer();
        boolean result = manche.lancer();

        // Assert
        assertFalse(result);
    }

    @Test
    public void Rouleau_DerniereManche_DeuxiemeLancer_PremierStrike_RetourneVrai() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(10);

        // Act
        derniereManche.lancer();
        boolean result = derniereManche.lancer();

        // Assert
        assertTrue(result);
    }

    @Test
    public void Rouleau_DerniereManche_DeuxiemeLancer_PremierStrike_VerifierScore() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(10, 5);

        // Act
        derniereManche.lancer();
        derniereManche.lancer();

        // Assert
        assertEquals(15, derniereManche.getScore());
    }

    @Test
    public void Rouleau_DerniereManche_TroisiemeLancer_PremierStrike_RetourneVrai() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(10);

        // Act
        derniereManche.lancer();
        derniereManche.lancer();
        boolean result = derniereManche.lancer();

        // Assert
        assertTrue(result);
    }

    @Test
    public void Rouleau_DerniereManche_TroisiemeLancer_PremierStrike_VerifierScore() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(10, 5, 2);

        // Act
        derniereManche.lancer();
        derniereManche.lancer();
        derniereManche.lancer();

        // Assert
        assertEquals(17, derniereManche.getScore());
    }

    @Test
    public void Rouleau_DerniereManche_TroisiemeLancer_Spare_RetourneVrai() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(5, 5);

        // Act
        derniereManche.lancer();
        derniereManche.lancer();
        boolean result = derniereManche.lancer();

        // Assert
        assertTrue(result);
    }

    @Test
    public void Rouleau_DerniereManche_TroisiemeLancer_Spare_VerifierScore() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(5, 5, 3);

        // Act
        derniereManche.lancer();
        derniereManche.lancer();
        derniereManche.lancer();

        // Assert
        assertEquals(13, derniereManche.getScore());
    }

    @Test
    public void Rouleau_DerniereManche_QuatriemeLancer_RetourneFaux() {
        // Arrange
        Mockito.when(generateur.genererQuillesAleatoires(10)).thenReturn(10, 5, 2);

        // Act
        derniereManche.lancer(); // premier lancer, strike
        derniereManche.lancer(); // deuxième lancer, 5 quilles
        derniereManche.lancer(); // troisième lancer, 2 quilles
        boolean result = derniereManche.lancer(); // quatrième lancer, ne devrait pas être autorisé

        // Assert
        assertFalse(result);
    }
}
