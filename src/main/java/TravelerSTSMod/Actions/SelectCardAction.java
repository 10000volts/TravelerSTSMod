package TravelerSTSMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SelectCardAction extends AbstractGameAction {
    private final Consumer<ArrayList<AbstractCard>> actionConsumer;
    private final CardGroup source;
    private final Predicate<AbstractCard> cardFilter;
    private final ArrayList<AbstractCard> cannotDuplicate = new ArrayList<>();

    private final String name;
    private final boolean anyNumber;
    private final boolean canPickZero;

    public SelectCardAction(String name, CardGroup source, int amount, Predicate<AbstractCard> cardFilter, Consumer<ArrayList<AbstractCard>> actionConsumer, boolean anyNumber, boolean canPickZero) {
        this.actionConsumer = actionConsumer;
        this.cardFilter = cardFilter;
        this.name = name;
        this.source = source;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public SelectCardAction(String name, CardGroup source, int amount, Predicate<AbstractCard> cardFilter, Consumer<ArrayList<AbstractCard>> actionConsumer) {
        this(name, source, amount, cardFilter, actionConsumer, false, false);
    }

    public SelectCardAction(String name, CardGroup source, int amount, Consumer<ArrayList<AbstractCard>> actionConsumer, boolean anyNumber, boolean canPickZero) {
        this(name, source, amount, null, actionConsumer, anyNumber, canPickZero);
    }

    public SelectCardAction(String name, CardGroup source, int amount, Consumer<ArrayList<AbstractCard>> actionConsumer) {
        this(name, source, amount, null, actionConsumer);
    }

    @Override
    public void update() {
        if (source == AbstractDungeon.player.hand) {
            if (this.duration == Settings.ACTION_DUR_FAST) {
                if (source.isEmpty()) {
                    isDone = true;
                    return;
                }
                if (this.cardFilter != null) {
                    for (AbstractCard c : source.group) {
                        if (!cardFilter.test(c)) {
                            cannotDuplicate.add(c);
                        }
                    }
                }
                if (cannotDuplicate.size() >= source.size()) {
                    isDone = true;
                    return;
                }
                source.group.removeAll(cannotDuplicate);

                if (source.size() <= this.amount && !this.anyNumber && !this.canPickZero) {
                    actionConsumer.accept(new ArrayList<>(source.group));
                    returnCards();
                    isDone = true;
                    return;
                }

                AbstractDungeon.handCardSelectScreen.open(this.name, amount, this.anyNumber, this.canPickZero);
                tickDuration();
                return;
            }
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                    actionConsumer.accept(AbstractDungeon.handCardSelectScreen.selectedCards.group);
                }
                returnCards();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                isDone = true;
            }
        } else {
            if (this.duration == Settings.ACTION_DUR_FAST) {
                if (source.isEmpty()) {
                    isDone = true;
                    return;
                }
                if (this.cardFilter != null) {
                    for (AbstractCard c : source.group) {
                        if (!cardFilter.test(c)) {
                            cannotDuplicate.add(c);
                        }
                    }
                }
                if (cannotDuplicate.size() >= source.size()) {
                    isDone = true;
                    return;
                }
                source.group.removeAll(cannotDuplicate);

                if (source.size() <= this.amount && !this.anyNumber && !this.canPickZero) {
                    actionConsumer.accept(new ArrayList<>(source.group));
                    returnCards();
                    isDone = true;
                    return;
                }
                if (!anyNumber) {
                    AbstractDungeon.gridSelectScreen.open(this.source, this.amount, "选择 " + this.amount + " 张牌" + this.name, false, false, false, false);
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.source, this.amount, true, "选择 " + this.amount + " 张牌" + this.name);
                }
                tickDuration();
                return;
            }
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                actionConsumer.accept(AbstractDungeon.gridSelectScreen.selectedCards);
            }
            returnCards();
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            isDone = true;
        }
        tickDuration();
    }


    private void returnCards() {
        for (AbstractCard c : this.cannotDuplicate) {
            source.addToTop(c);
        }
        AbstractDungeon.player.hand.refreshHandLayout();
    }
}
