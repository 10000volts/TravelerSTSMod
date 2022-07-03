package TravelerSTSMod.Relics;

import TravelerSTSMod.Cards.Abstract.PersonalityCard;
import TravelerSTSMod.Powers.WhisperPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class Trinity extends CustomRelic {
    // 遗物ID
    public static final String ID = "TravelerSTSMod:Trinity";
    // 图片路径
    private static final String IMG_PATH = "TravelerSTSModResources/img/relics/Trinity.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.COMMON;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;

    public Trinity() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 0;
    }

    public boolean canSpawn() {
        return true;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (c instanceof PersonalityCard) {
            if (counter == 2) {
                flash();
                counter = 0;

                ArrayList<AbstractMonster> am = AbstractDungeon.getMonsters().monsters;
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    if (!mo.isDeadOrEscaped()) {
                        addToBot(new ApplyPowerAction(mo, AbstractDungeon.player,
                                new WhisperPower(mo, AbstractDungeon.player, 3), 3,
                                true, AbstractGameAction.AttackEffect.NONE));
                    }
                }
            } else {
                counter += 1;
            }
        }
    }

    public AbstractRelic makeCopy() {
        return new Trinity();
    }
}
