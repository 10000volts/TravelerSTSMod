package TravelerSTSMod.Potions;

import TravelerSTSMod.ModCore.TravelerMod;
import TravelerSTSMod.Relics.BookAndQuill;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.MasterReality;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class BottleSpell extends AbstractPotion {
    public static final String POTION_ID = "TravelerSTSMod:BottleSpell";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public BottleSpell() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.CARD,
                AbstractPotion.PotionColor.EXPLOSIVE);
        this.labOutlineColor = TravelerMod.MY_COLOR;
        this.isThrown = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        int p = this.potency;

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
                        for (int i = 0; i < p; ++i) {
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

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new BottleSpell();
    }
}
