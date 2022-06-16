package TravelerSTSMod.ModCore;
import TravelerSTSMod.Cards.*;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Relics.BookAndQuill;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import basemod.BaseMod;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

import static com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS;

@SpireInitializer
public class TravelerMod implements EditCharactersSubscriber,EditCardsSubscriber,
        EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber {
    public static final Logger logger = LogManager.getLogger(TravelerMod.class);

    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "TravelerSTSModResources/img/charSelect/button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "TravelerSTSModResources/img/charSelect/travelerPortrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "TravelerSTSModResources/img/512/bg_attack.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "TravelerSTSModResources/img/512/bg_power.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "TravelerSTSModResources/img/512/bg_skill.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "TravelerSTSModResources/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "TravelerSTSModResources/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "TravelerSTSModResources/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "TravelerSTSModResources/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "TravelerSTSModResources/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = "TravelerSTSModResources/img/char/cost_orb.png";
    public static final Color MY_COLOR = new Color(103.0F / 255.0F, 83.0F / 255.0F, 161.0F / 255.0F, 1.0F);

    public TravelerMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new TravelerMod();
    }


    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Traveler(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT,
                Traveler.Enums.TRAVELER);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new BookAndQuill(), RelicType.SHARED);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new InkDrop());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new EmptySpell());
        BaseMod.addCard(new HeartLock());
        BaseMod.addCard(new Melody());
    }

    @Override
    public void receiveEditStrings() {
//        switch (Settings.language){
//        }
        String llPath = "TravelerSTSModResources/localization/";
        String lang = "ZHS";
        String cardStrings = Gdx.files.internal(llPath + lang + "/card.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String relicStrings = Gdx.files.internal(llPath + lang + "/relic.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String charStrings = Gdx.files.internal(llPath + lang + "/characters.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class, charStrings);
        String powerStrings = Gdx.files.internal(llPath + lang + "/power.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

        BaseMod.addColor(Traveler.Enums.TRAVELER_CARD, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,
                BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENEYGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,
                SMALL_ORB);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String llPath = "TravelerSTSModResources/localization/";
        String lang = "ZHS";

        String json = Gdx.files.internal(llPath + lang + "/keyword.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("travelerstsmod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
}
