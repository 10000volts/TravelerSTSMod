package TravelerSTSMod.Cards;

import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.QuickCastingPower;
import TravelerSTSMod.Powers.VoiceOfDarkPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QuickCasting extends CustomCard {
    public static final String ID = "TravelerSTSMod:QuickCasting";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/QuickCasting.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;

    public QuickCasting()  {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.isSeen = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new QuickCastingPower(p)));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c.hasTag(Traveler.Enums.TRAVELER_SPELL))
                        c.modifyCostForCombat(-9);
                }
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    if (c.hasTag(Traveler.Enums.TRAVELER_SPELL))
                        c.modifyCostForCombat(-9);
                }
                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    if (c.hasTag(Traveler.Enums.TRAVELER_SPELL))
                        c.modifyCostForCombat(-9);
                }
                for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                    if (c.hasTag(Traveler.Enums.TRAVELER_SPELL))
                        c.modifyCostForCombat(-9);
                }
                this.isDone = true;
            }
        });
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    public AbstractCard makeCopy() {
        return new QuickCasting();
    }
}
