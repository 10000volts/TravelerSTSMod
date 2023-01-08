package TravelerSTSMod.Powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class VoiceOfDarkPower extends AbstractPower {
    public static final String POWER_ID = "TravelerSTSMod:VoiceOfDark";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public VoiceOfDarkPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = 1;

        // 添加一大一小两张能力图
        String path128 = "TravelerSTSModResources/img/powers/128/VoiceOfDark.png";
        String path48 = "TravelerSTSModResources/img/powers/48/VoiceOfDark.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(WhisperPower.POWER_ID) && source == this.owner && target != this.owner && !target.hasPower("Artifact")) {
            flash();
            addToBot(new ApplyPowerAction(target, this.owner, new PoisonPower(target, this.owner,
                    power.amount * this.amount), power.amount * this.amount,true,
                    AbstractGameAction.AttackEffect.POISON));
        }
    }
//    public void atEndOfTurn(boolean isPlayer) {
//        super.atEndOfTurn(isPlayer);
//        if (isPlayer) {
//            AbstractPlayer p = AbstractDungeon.player;
//            for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
//                for (AbstractPower pw : m.powers) {
//                    if (pw.ID.equals(WhisperPower.POWER_ID)) {
//                        addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, pw.amount * this.amount),
//                                pw.amount * this.amount,true,
//                                AbstractGameAction.AttackEffect.POISON));
//                        break;
//                    }
//                }
//            }
//        }
//    }
}
