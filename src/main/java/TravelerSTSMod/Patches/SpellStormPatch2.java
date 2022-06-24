package TravelerSTSMod.Patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;


@SpirePatch(clz = CardCrawlGame.class, method = "loadPlayerSave")
public class SpellStormPatch2 {
    @SpirePrefixPatch
    public static void Postfix(CardCrawlGame __instance, AbstractPlayer p) {
        SpellStormPatch.spellUsed = 0;
    }
}
