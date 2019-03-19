<template>
    <div class="tableContainer">
        <el-table :data="tableData" stripe border >
            <el-table-column prop="ville" label="Serie" sortable>             
            </el-table-column>
            <el-table-column prop="joueur" label="Utilisateur" sortable>
            </el-table-column>
            <el-table-column prop="difficulty" label="Difficulté" sortable >
            </el-table-column>
            <el-table-column prop="photos.length" label="Longueur de la série" sortable >
            </el-table-column>
            <el-table-column prop="score" label="Score" sortable>
            </el-table-column>
        </el-table>
    </div>
    
</template>

<script>

    export default {
        name: 'scores',
        data() {
            return {
                scores: [],
                tableData: []
            }
        },

        methods: {

            //get the score data from the api
            getScore: function(){
                let $this = this;
                this.axios.get('http://localhost:8083/game/score/')
                .then((response) => {
                    $this.scores = response.data
                    $this.processScore();
                })
                .catch((err) => {
                    $this.$message.error("Erreur lors de la récupération des scores. Réessayez plus tard :  " + err);
                });
            },

            //process the data for display
            processScore: function(){

                let $this = this;
                for(let scoreIndex = 0; scoreIndex < $this.scores.length; scoreIndex ++){
                    let score = $this.scores[scoreIndex];
                    if ( score.ville && score.joueur && score.difficulty >= 0 && score.score >= 0 && score.saveScore){
                        let newScore = score;
                        if (score.difficulty == 0) {
                            score.difficulty = "Parisien";
                            score.score = score.score + ' / ' + score.photos.length * 20;
                        }
                        else if (score.difficulty == 1){ 
                            score.difficulty = "Touriste";
                            score.score = score.score + ' / ' + (score.photos.length * 20 * 2);
                        }
                        else if (score.difficulty == 2){ 
                            score.difficulty = "Guide du routard";
                            score.score = score.score + ' / ' + (score.photos.length * 20 * 3);
                        }
                        $this.tableData.push(score);
                    }
                }
            }
        },
        created: function() {
            this.getScore()
        }
    }
</script>

<style scoped>


    .el-table{
        width: 100%;
    }

    .tableContainer{
        width: 100%;
    
    }
</style>