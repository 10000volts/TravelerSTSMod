package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.PersonalityCard;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class PrideForm extends PersonalityCard {
    public static final String ID = "TravelerSTSMod:PrideForm";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/PrideForm.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 3;

    public boolean isMadeByPrideForm;

    public PrideForm(boolean ethereal, boolean isMadeByPrideForm) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ethereal);

        this.isSeen = true;

        this.isMadeByPrideForm = isMadeByPrideForm;
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
//            if (p.hasPower(PrideFormPowerUpgraded.POWER_ID)) return;
//            if (p.hasPower(PrideFormPower.POWER_ID)) {
//                addToBot(new RemoveSpecificPowerAction(p, p, PrideFormPower.POWER_ID));
//            }
            addToBot(new ApplyPowerAction(p, p, new PrideFormPowerUpgraded(p), 1));
        } else if (!p.hasPower(PrideFormPowerUpgraded.POWER_ID)) {
//            if (p.hasPower(PrideFormPower.POWER_ID)) return;
            addToBot(new ApplyPowerAction(p, p, new PrideFormPower(p), 1));
        }
        // addToBot(new DrawCardAction(p, this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new PrideForm(this.isEthereal, this.isMadeByPrideForm);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (!this.isMadeByPrideForm) {
                this.isEthereal = false;
            }
            this.isInnate = true;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void onAct(AbstractCard c, ArrayList<AbstractCard> cards, AbstractMonster m) {
        super.onAct(c, cards, m);
        updateCost(-1);
    }
}
