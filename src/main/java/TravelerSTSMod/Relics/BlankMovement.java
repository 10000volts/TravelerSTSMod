package TravelerSTSMod.Relics;

import TravelerSTSMod.Powers.SentencePower;
import TravelerSTSMod.Powers.WhisperPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class BlankMovement extends CustomRelic {
    // 遗物ID
    public static final String ID = "TravelerSTSMod:BlankMovement";
    // 图片路径
    private static final String IMG_PATH = "TravelerSTSModResources/img/relics/BlankMovement.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public BlankMovement() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, AbstractDungeon.player,
                         new WhisperPower(mo, AbstractDungeon.player, 10), 10,
                        true, AbstractGameAction.AttackEffect.NONE));
            }
        }

//        int hpmax = 0;
//        AbstractMonster mmax = null;
//        ArrayList<AbstractMonster> am = AbstractDungeon.getMonsters().monsters;
//        for (AbstractMonster m : am) {
//            if (m.maxHealth > hpmax) {
//                hpmax = m.maxHealth;
//                mmax = m;
//            }
//        }
//        if (mmax != null) {
//            addToBot(new ApplyPowerAction(mmax, AbstractDungeon.player,
//                    new WhisperPower(mmax, AbstractDungeon.player, 11), 11,
//                    true, AbstractGameAction.AttackEffect.NONE));}
    }

    public AbstractRelic makeCopy() {
        return new BlankMovement();
    }
}
