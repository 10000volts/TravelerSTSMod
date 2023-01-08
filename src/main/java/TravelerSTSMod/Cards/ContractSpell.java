package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.IUsePositionCard;
import TravelerSTSMod.Cards.Abstract.SpellCard;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.SentencePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ContractSpell extends SpellCard implements IUsePositionCard {
    public static final String ID = "TravelerSTSMod:ContractSpell";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/ContractSpell.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;


    public ContractSpell(int influenced) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, influenced);

        this.isSeen = true;

        this.baseBlock = 6;
        this.block = 6;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ContractSpell(this.costInfluencedLastTurn);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void position(int p) {
        CardGroup g = AbstractDungeon.player.hand;
        // 只有这张卡
        if (g.size() == 1) return;
        if (p > 0) {
            g.group.get(p-1).setCostForTurn(0);
        }
        if (p + 1 < g.size()) {
            g.group.get(p+1).setCostForTurn(0);
        }
    }
}
