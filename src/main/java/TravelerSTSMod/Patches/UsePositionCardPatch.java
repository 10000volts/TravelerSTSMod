package TravelerSTSMod.Patches;

import TravelerSTSMod.Cards.Abstract.IUsePositionCard;
import TravelerSTSMod.Cards.Abstract.PersonalityCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

@SpirePatch(clz = AbstractPlayer.class, method = "useCard", paramtypez =
        {AbstractCard.class, AbstractMonster.class, int.class})
public class UsePositionCardPatch {
    @SpirePrefixPatch
    public static void Prefix(AbstractPlayer self, AbstractCard c, AbstractMonster m, int e) {
        if (!self.hand.contains(c)) return;
        if (c instanceof IUsePositionCard) {
            ((IUsePositionCard)c).position(self.hand.group.indexOf(c));
        }
    }
}
