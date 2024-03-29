package TravelerSTSMod.Powers;

import TravelerSTSMod.Cards.PrideForm;
import TravelerSTSMod.ModCore.TravelerMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PrideFormPowerUpgraded extends AbstractPower {
    public static final String POWER_ID = "TravelerSTSMod:PrideFormUpgraded";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PrideFormPowerUpgraded(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = 1;

        // 添加一大一小两张能力图
        String path128 = "TravelerSTSModResources/img/powers/128/PrideForm.png";
        String path48 = "TravelerSTSModResources/img/powers/48/PrideForm.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount +
            DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < this.amount; ++i) {
            // 排除自己
            AbstractCard c = TravelerMod.personalityPool.get(
                    AbstractDungeon.cardRandomRng.random(TravelerMod.personalityPool.size() - 1)).makeCopy();
            c.upgrade();
            c.isEthereal = true;
            if (c instanceof PrideForm) {
                ((PrideForm) c).isMadeByPrideForm = true;
            }

            addToBot(new MakeTempCardInHandAction(c, true));
        }
    }
}
