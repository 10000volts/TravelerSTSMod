package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.ISpecificSentence;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.SentencePower;
import TravelerSTSMod.Relics.BookAndQuill;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Alchemy extends CustomCard implements ISpecificSentence {
    public static final String ID = "TravelerSTSMod:Alchemy";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Alchemy.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;

    public Alchemy() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.isSeen = true;

        this.baseBlock = 15;
        this.baseMagicNumber = 15;
        this.magicNumber = 15;
        this.exhaust = true;

        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (SentencePower.getSentence(AbstractDungeon.player) == 3) {
            AbstractDungeon.player.gainGold(this.magicNumber);
        }
    }

    public AbstractCard makeCopy() {
        return new Alchemy();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(5);
            upgradeMagicNumber(5);
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (SentencePower.getSentence(AbstractDungeon.player) == 3) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public int getSpecificSentence(boolean general) {
        return 3;
    }
}
