<template>
  <div id="app">
    <h1>Lang Reader</h1>
    <hr>
    <a href="#" @click.prevent>Library</a>
    <hr>
    <textarea v-model="input"
              :disabled="isEnriched"
              rows="20"
              cols="100"/>
    <br>
    <button @click="submit" :disabled="isEnriched">Submit</button>
    <p v-if="errorMsg">Error: {{ errorMsg }} </p>
    <ReadingArea v-if="isEnriched"
                 :strings="strObjs"
                 :stringCounts="strCounts"
                 @update-word-type="updateWords"
                 @reset-word="resetWord"/>
  </div>
</template>

<script>
  import {parseParagraph} from "./utils";
  import {enrichWordsFromDB, removeFromDB, updateDB} from './db-utils';
  import ReadingArea from "./ReadingArea";

  export default {
    components: {ReadingArea},
    data() {
      return {
        input: `The Dursleys are a well-to-do, status-conscious family living in Surrey, England. Eager to keep up proper appearances, they are embarrassed by Mrs. Dursley’s eccentric sister, Mrs. Potter, whom for years Mrs. Dursley has pretended not to know. On his way to work one ordinary morning, Mr. Dursley notices a cat reading a map. He is unsettled, but tells himself that he has only imagined it. Then, as Mr. Dursley is waiting in traffic, he notices people dressed in brightly colored cloaks. Walking past a bakery later that day, he overhears people talking in an excited manner about his sister-in-law’s family, the Potters, and the Potters’ one-year-old son, Harry. Disturbed but still not sure anything is wrong, Mr. Dursley decides not to say anything to his wife. On the way home, he bumps into a strangely dressed man who gleefully exclaims that someone named “You-Know-Who” has finally gone and that even a “Muggle” like Mr. Dursley should rejoice. Meanwhile, the news is full of unusual reports of shooting stars and owls flying during the day.

That night, as the Dursleys are falling asleep, Albus Dumbledore, a wizard and the head of the Hogwarts wizardry academy, appears on their street. He shuts off all the streetlights and approaches a cat that is soon revealed to be a woman named Professor McGonagall (who also teaches at Hogwarts) in disguise. They discuss the disappearance of You-Know-Who, otherwise known as Voldemort. Dumbledore tells McGonagall that Voldemort killed the Potter parents the previous night and tried to kill their son, Harry, as well, but was unable to. Dumbledore adds that Voldemort’s power apparently began to wane after his failed attempt to kill Harry and that he retreated. Dumbledore adds that the baby Harry can be left on the Dursleys’ doorstep. McGonagall protests that Harry cannot be brought up by the Dursleys. But Dumbledore insists that there is no one else to take care of the child. He says that when Harry is old enough, he will be told of his fate. A giant named Hagrid, who is carrying a bundle of blankets with the baby Harry inside, then falls out of the sky on a motorcycle. Dumbledore takes Harry and places him on the Dursley’s doorstep with an explanatory letter he has written to the Dursleys, and the three part ways.
Analysis

This first chapter introduces a number of elements important to the story without explaining them, stirring our curiosity and emphasizing the idea of this mysterious other world that is far away from everyday reality. The word “Muggle,” for example, is used repeatedly, and though we hear it referred to humans, we do not fully understand its associations. However, even though the meaning of the word escapes us, we feel the importance of the distinction between Muggles and non-Muggles. Similarly, we feel that there is something meaningful about Harry’s lightning-bolt forehead scar, though we cannot explain it any more than Harry can. Like Harry, we are forced to accept a lot of information we cannot process at first, so that we are humbled before the complexity of the wizards’ world. It is important that Harry’s initiation into magic is gradual so that we can identify with what he is feeling; like us, Harry is entering an unknown world.`,
        isEnriched: false,
        strObjs: [],
        strCounts: [],
        errorMsg: ''
      }
    },
    methods: {
      submit() {
        this.processInput();
        enrichWordsFromDB(this.getWordObjs(), this.updateAfterEnriching); // callback
      },
      updateAfterEnriching(enrichedWordObjs) {
        this.updateStrObjs(enrichedWordObjs);
        this.isEnriched = true;
      },
      processInput() {
        const paragraphTexts = this.input.split('\n');
        paragraphTexts.forEach((paragraphText) => {
          let result = parseParagraph(paragraphText);
          this.strObjs = [...this.strObjs, ...result];
          this.strCounts.push(result.length);
        });
      },
      updateWords(theWordObj, newType) {
        const oldType = theWordObj.type;
        // update Vue instance first
        theWordObj.type = newType;
        this.updateStrObjs([theWordObj]);
        // update DB
        const isNew = (oldType === null);
        const dbObject = {word: theWordObj.word.toLowerCase(), type: newType};
        updateDB(dbObject, isNew);
      },
      resetWord(removedWordObj) {
        if (removedWordObj.type !== null) { // start reset only if it's not a new word already
          removedWordObj.type = null;
          this.updateStrObjs([removedWordObj]);
          removeFromDB(removedWordObj);
        }
      },
      updateStrObjs(wordObjs) { // TODO: improve performance here... make it async?
        // ORIGINAL
        // wordObjs.forEach(wordObj => {
        //   const selectedStrObjs =
        //     this.strObjs.filter(strObj => {
        //       return strObj.isWord;
        //     })
        //       .filter(strWordObj => {
        //         return (strWordObj.str.word.toLowerCase() === wordObj.word.toLowerCase());
        //       });
        //   selectedStrObjs.forEach(selectedStrObj => {
        //     selectedStrObj.str.type = wordObj.type;
        //   });
        // });
        // NOT MUCH BETTER
        wordObjs.forEach(wordObj => {
          this.strObjs.forEach(strObj => {
            if (strObj.str.word.toLowerCase() === wordObj.word.toLowerCase()) {
              strObj.str.type = wordObj.type;
            }
          });
        });
      },
      getWordObjs() {
        return this.strObjs.filter(strObj => {
          return strObj.isWord;
        })
          .map(wordStrObj => {
            return wordStrObj.str;
          }) // remove duplicates - for performance reasons (ca. 3x quicker)
          .filter((wordObj, index, self) =>
            index === self.findIndex((comparedWordObj) => (
              wordObj.word.toLowerCase() === comparedWordObj.word.toLowerCase()
            ))
          );
      }
    }
  }
</script>

<style>
  textarea {
    font-family: arial, sans-serif;
    font-size: 1.1em;
    width: 740px;
    height: 200px;
  }
</style>
