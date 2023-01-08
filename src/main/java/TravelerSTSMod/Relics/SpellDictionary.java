package TravelerSTSMod.Relics;

import TravelerSTSMod.ModCore.TravelerMod;
import TravelerSTSMod.Powers.WhisperPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.MasterReality;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class SpellDictionary extends CustomRelic {
    // 遗物ID
    public static final String ID = "TravelerSTSMod:SpellDictionary";
    // 图片路径
    private static final String IMG_PATH = "TravelerSTSModResources/img/relics/SpellDictionary.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;

    public SpellDictionary() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new AbstractGameAction() {
            private boolean retrieveCard = false;

            {
                this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
                this.duration = Settings.ACTION_DUR_FAST;
                this.amount = 1;
            }

            @Override
            public void update() {
                if (this.duration == Settings.ACTION_DUR_FAST) {
                    AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], false);
                    tickDuration();
                    return;
                }
                if (!this.retrieveCard) {
                    if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                        AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                        if (AbstractDungeon.player.hasPower(MasterReality.ID))
                            disCard.upgrade();
                        disCard.current_x = -1000.0F * Settings.xScale;
                        if (this.amount == 1) {
                            if (AbstractDungeon.player.hand.size() < 10) {
                                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                            } else {
                                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                            }
                        } else if (AbstractDungeon.player.hand.size() + this.amount <= 10) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        } else if (AbstractDungeon.player.hand.size() == 9) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        }
                        AbstractDungeon.cardRewardScreen.discoveryCard = null;
                    }
                    this.retrieveCard = true;
                }
                tickDuration();
            }

            private ArrayList<AbstractCard> generateCardChoices() {
                ArrayList<AbstractCard> derp = new ArrayList<>();
                while (derp.size() != 3) {
                    boolean dupe = false;
                    AbstractCard tmp = TravelerMod.spellPool.get(
                            AbstractDungeon.cardRandomRng.random(TravelerMod.spellPool.size() - 1)).makeCopy();
                    for (AbstractCard c : derp) {
                        if (c.cardID.equals(tmp.cardID)) {
                            dupe = true;
                            break;
                        }
                    }
                    if (!dupe) {
                        AbstractCard c = tmp.makeCopy();
                        c.setCostForTurn(0);
                        derp.add(c);
                    }
                }
                return derp;
            }
        });
    }

    public AbstractRelic makeCopy() {
        return new SpellDictionary();
    }
}
