package TravelerSTSMod.Cards;

import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.GazeOfAbyssPower;
import TravelerSTSMod.Powers.GazeOfAbyssPowerUpgraded;
import TravelerSTSMod.Powers.VoiceOfDarkPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GazeOfAbyss extends CustomCard {
    public static final String ID = "TravelerSTSMod:GazeOfAbyss";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/GazeOfAbyss.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;

    public GazeOfAbyss()  {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            if (p.hasPower("TravelerSTSMod:GazeOfAbyssUpgraded")) return;
            if (p.hasPower("TravelerSTSMod:GazeOfAbyss")) {
                addToBot(new RemoveSpecificPowerAction(p, p, "TravelerSTSMod:GazeOfAbyss"));
            }
            addToBot(new ApplyPowerAction(p, p, new GazeOfAbyssPowerUpgraded(p)));
        } else if (!p.hasPower("TravelerSTSMod:GazeOfAbyssUpgraded")) {
            if (p.hasPower("TravelerSTSMod:GazeOfAbyss")) return;
            addToBot(new ApplyPowerAction(p, p, new GazeOfAbyssPower(p)));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new GazeOfAbyss();
    }
}
