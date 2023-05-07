package TravelerSTSMod.Cards;

import TravelerSTSMod.Actions.SelectCardAction;
import TravelerSTSMod.Actions.StoreAction;
import TravelerSTSMod.Characters.Traveler;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Analgesia extends CustomCard {

    public static final String ID = "TravelerSTSMod:Analgesia";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Analgesia.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 2;

    public Analgesia() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.isSeen = true;
        this.magicNumber = this.baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hand.isEmpty() && !p.drawPile.isEmpty()) {
            ArrayList<AbstractCard> placeList = new ArrayList<>();
            Consumer<ArrayList<AbstractCard>> actionConsumer = cards -> {
                placeList.add(cards.get(0));
                p.hand.addToHand(cards.get(0));
            };
            Consumer<ArrayList<AbstractCard>> actionConsumer2 = cards -> this.addToBot(new StoreAction(cards, placeList.get(0)));
            this.addToBot(new SelectCardAction("作为容器", p.hand, 1, null, actionConsumer, false, false));
            this.addToBot(new SelectCardAction("寄存", p.drawPile, this.magicNumber, null, actionConsumer2, false, false));
        }
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}