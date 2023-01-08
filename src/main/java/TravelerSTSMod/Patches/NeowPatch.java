package TravelerSTSMod.Patches;

import TravelerSTSMod.Cards.Abstract.PersonalityCard;
import TravelerSTSMod.Relics.SoulVessel;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

@SpirePatch(clz = AbstractPlayer.class, method = "useCard", paramtypez =
        {AbstractCard.class, AbstractMonster.class, int.class})
public class NeowPatch {
//    @SpirePrefixPatch
//    public static void Prefix(AbstractPlayer self, AbstractCard c, AbstractMonster m, int e) {
//        if (!self.hand.contains(c)) return;
//        if (!(c instanceof PersonalityCard) || self.hasRelic(SoulVessel.ID)) {
//            ArrayList<PersonalityCard> pcs = new ArrayList<>();
//            for (AbstractCard pc : self.hand.group) {
//                if (pc instanceof PersonalityCard) {
//                    // 触发过程中hand.group可能会变，先存一下
//                    pcs.add((PersonalityCard) pc);
//                }
//            }
//
//            for (PersonalityCard pc : pcs) {
//                pc.onCheckAct(c, m);
//            }
//        }
//    }
}
