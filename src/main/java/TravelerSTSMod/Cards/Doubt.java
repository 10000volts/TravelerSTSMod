package TravelerSTSMod.Cards;

import TravelerSTSMod.Actions.SelectCardAction;
import TravelerSTSMod.Actions.StoreAction;
import TravelerSTSMod.Characters.Traveler;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Doubt extends CustomCard {

    public static final String ID = "TravelerSTSMod:Doubt";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Doubt.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADE_MAGIC_NUMBER = 1;

    public Doubt() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hand.isEmpty() && !p.drawPile.isEmpty()) {
            ArrayList<AbstractCard> placeList = new ArrayList<>();
            Consumer<ArrayList<AbstractCard>> actionConsumer = cards -> placeList.add(cards.get(0));
            Consumer<ArrayList<AbstractCard>> actionConsumer2 = cards -> {
                this.addToBot(new StoreAction(cards, placeList.get(0)));
                this.addToBot(new DrawCardAction(cards.size()));
            };
            this.addToBot(new SelectCardAction("作为容器", p.drawPile, 1, null, actionConsumer, false, false));
            this.addToBot(new SelectCardAction("寄存", p.hand, this.magicNumber, null, actionConsumer2, false, false));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
        }
    }
}