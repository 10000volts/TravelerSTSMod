package TravelerSTSMod.Cards;

import TravelerSTSMod.Actions.EmptySpellAction;
import TravelerSTSMod.Cards.Abstract.PersonalityCard;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.ChantPower;
import TravelerSTSMod.Powers.SentencePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class EmptySpell extends PersonalityCard {
    public static final String ID = "TravelerSTSMod:EmptySpell";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/EmptySpell.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 0;

    // 本回合是否触发过行动
    private boolean acted;

    protected int costInfluencedLastTurn;

    public EmptySpell(int influenced) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, false);
        this.costInfluencedLastTurn = influenced;

        this.baseMagicNumber = 2;
        this.magicNumber = 2;
        this.tags.add(Traveler.Enums.TRAVELER_SPELL);
        this.acted = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (SentencePower.getSentence(p) == 4) {
            addToBot(new DrawCardAction(p, this.magicNumber));
        }
    }

    public AbstractCard makeCopy() {
        return new EmptySpell(this.costInfluencedLastTurn);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void onAct(AbstractCard c, ArrayList<AbstractCard> cards, AbstractMonster m) {
        super.onAct(c, cards, m);
        if (!this.acted) {
//            if (cards.isEmpty())  {
//                return;
//            }
//
//            AbstractPlayer p = AbstractDungeon.player;
//            ArrayList<AbstractCard> handTemp = new ArrayList<>();
//            ArrayList<AbstractCard> cannotExhaust = new ArrayList<>();
//            for (AbstractCard cc : p.hand.group) {
//                handTemp.add(cc);
//                if (!cards.contains(cc)) {
//                    cannotExhaust.add(cc);
//                }
//            }
//            p.hand.group.removeAll(cannotExhaust);
//
//            // 加回来
//            addToTop(new AbstractGameAction() {
//                @Override
//                public void update() {
//                    // 保持其他手牌顺序
//                    p.hand.group.clear();
//                    for (AbstractCard cc : handTemp)
//                        if (!p.exhaustPile.contains(cc))
//                            p.hand.addToTop(cc);
//                    p.hand.refreshHandLayout();
//                    isDone = true;
//                }
//            });
//            addToTop(new ExhaustAction(1, true));
            // 上面的优雅一点但有bug
            addToBot(new EmptySpellAction(cards));
        }
        this.acted = true;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();

        // 修正cost
        this.cost += this.costInfluencedLastTurn;
        this.costInfluencedLastTurn = 0;
        resetAttributes();
        this.updateCost(this.cost);
        applyPowers();

        this.acted = false;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for (AbstractCard c: AbstractDungeon.player.hand.group) {
            if (c.tags.contains(Traveler.Enums.TRAVELER_PERSONALITY) && !c.equals(this)) {
                if (!this.acted) {
                    this.glowColor = P_BORDER_GLOW_COLOR.cpy();
                } else {
                    this.glowColor = P_BORDER_GLOW_COLOR2.cpy();
                }
                return;
            }
        }
        if (SentencePower.getSentence(AbstractDungeon.player) == 4) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void updateCost(int amt) {
        int tmpCost = 0;
        int diff = this.cost - this.costForTurn;
        tmpCost += amt;
        if (tmpCost <= 0)
            tmpCost = 0;
        else {
            for (AbstractPower pw: AbstractDungeon.player.powers) {
                if (pw.ID.equals(ChantPower.POWER_ID)) {
                    int m = Math.min(tmpCost, pw.amount);
                    tmpCost -= m;
                    this.costInfluencedLastTurn += m;
                    break;
                }
            }
        }
        if (tmpCost != this.cost) {
            this.isCostModified = true;
            this.cost = tmpCost;
            this.costForTurn = this.cost - diff;
            if (this.costForTurn < 0)
                this.costForTurn = 0;
        }
    }
}
